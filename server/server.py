#!/usr/bin/python3
from flask import Flask, request, Response, send_file,jsonify,render_template
from flask_cors import cross_origin
from werkzeug.utils import secure_filename
import pymysql
import json
import time
import os
import datetime
from operator import itemgetter, attrgetter

dname='root'
dpassword='root'
dbase='smartsign'

class DateEncoder(json.JSONEncoder):  
    def default(self, obj):  
        if isinstance(obj, datetime.datetime):  
            return obj.strftime('%Y-%m-%d %H:%M:%S')  
        elif isinstance(obj, datatime.date):  
            return obj.strftime("%Y-%m-%d")  
        else:  
            return json.JSONEncoder.default(self, obj) 

app = Flask(__name__)
@app.route('/', methods=['GET', 'POST', 'OPTIONS'])
@cross_origin()
def login():

    if request.method == 'GET':
        return send_file("index.html")
    if request.method == 'POST':
        postData = json.loads(request.get_data().decode('utf-8'))
        command = postData['type']
        status=200
        if command == "slogin":
            number = postData['number']
            password = postData['password']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute(
                "select  sno from student where snumber='%s' and spassword='%s'" % (number, password))
                data = cursor.fetchone()
                if data:
                    result={"success":"true"}
                else:
                    status=400
                    result = {"success":"false","error": "login wrong"}
            except:
                status=400
                result={"success":"false","error":"db select error"}
            resp = Response(json.dumps(result), status=400,
                                mimetype="application/json")
            db.close()
            return resp
        elif command == "sregister":
            name = postData['name']
            sex=postData['sex']
            school=postData['school']
            sid=postData['sid']
            number=postData['number']
            password = postData['password']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute(
                "select sno from student where snumber='%s'" % (number))
                data = cursor.fetchone()
                if not data:
                    try:
                        cursor.execute(
                            "insert into student(sno,sname,sex,snumber,sschool,spassword,sfaceregistercount) values('%s','%s','%d','%s','%s','%s','%d')" % (sid,name,sex,number,school,password,0))
                        db.commit()
                        result={"success":"true"}
                    except:
                        db.rollback()
                        status=400
                        result={"success":"false","error":"db insert error"}
                elif not data[0]:
                    try:
                        cursor.execute("update student set sno='%s',sname='%s',sex='%d',sschool='%s',spassword='%s' where snumber='%s'"%(sid,name,sex,school,password,number))
                        db.commit()
                        result={"success":"true"}
                    except:
                        status=400
                        result={"success":"false","error":"update error"}
                else:
                    status=400
                    result = {"success":"false","error":"this number has been registered"}
            except:
                status=400
                result={"success":"false","error":"db select error"}
            db.close() 
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="sfetchCourse":
            number=postData['number']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            courseList=list()
            try:
                cursor.execute(
                "select cno from sc where snumber='%s'" % (number))
                for line in cursor.fetchall():
                    cno=line[0]
                    cursor.execute("select cname,ctime,cstart,cend,csigntime,cnth from course where cno='%s'"%(cno))
                    data=cursor.fetchone()
                    cname=data[0]
                    ctime=data[1]
                    cstart=data[2]
                    cend=data[3]
                    csigntime=data[4]
                    cnth=data[5]
                    cursor.execute("select csigntimelast from coursesign where cno='%s' and cnth='%d'"%(cno,cnth))
                    data=cursor.fetchone()
                    if data:
                        timelast=data[0]
                    else:
                        timelast=0
                    coursealone={"name":cname,"time":ctime,"start":cstart,"end":cend,"signtime":csigntime,"SigntimeLast":timelast}
                    courseList.append(coursealone)
                result={"success":"true","course":courseList}
            except:
                status=400
                result={"success":"false","error":"maybe db error"}
            db.close() 
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="saddCourse":
            number=postData['number']
            course=postData['course']
            teacher=postData['teacher']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute("select cno from course where cname='%s'"%(course))
                cno=cursor.fetchone()[0]
                cursor.execute("select snumber from sc where snumber='%s' and cno='%s'"%(number,cno))
                data1=cursor.fetchone()
                cursor.execute("select snumber from studentapply where snumber='%s' and cno='%s'"%(number,cno))
                data2=cursor.fetchone()
                if not data1 and not data2:
                    cursor.execute(
                    "insert into studentapply(cno,snumber,tname,applystate) values ('%s','%s','%s','%d')" % (cno,number,teacher,0))
                    db.commit()
                    result={"success":"true"}
                elif data1:
                    status=400
                    result={"success":"false","error":"course already exists"}
                elif data2:
                    status=400
                    result={"success":"false","error":"course already applied"}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="sfetchSign":
            course=postData['course']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute("select cno,cnth from course where cname='%s'"%(course))
                data=cursor.fetchone()
                cno=data[0]
                cnth=data[1]
                cursor.execute("select csignposi,csigntime.csigntimelast from coursesign where cno='%s' and cnth='%d'"%(cno,cnth)%(cno,cnth))
                data=cursor.fetchone()
                csignposi=data[0]
                csigntime=data[1]
                csigntimelast=data[2]
                result={"success":"true","position":csignposi,"time":csigntime,"timelast":csigntimelast}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="ssign":
            number=postData['snumber']
            course=postData['course']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            now=datetime.datetime.now()
            now = now.strftime("%Y-%m-%d %H:%M:%S")
            try:
                cursor.execute("select cno,cnth from course where cname='%s'"%(course))
                data=cursor.fetchone()
                cno=data[0]
                cnth=data[1]
                cursor.execute("insert into studentsign(cno,snumber,cnth,csignetime) values ('%s','%s','%d','%s')"%(cno,number,cnth,now))
                db.commit()
                result={"success":"true"}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tlogin":
            name=postData['name']
            password=postData['password']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            result={"true":"success"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tfetchCourse":
            name=postData['name']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            whatday=datetime.datetime.now().weekday()
            whatday+=1
            try:
                courselist=list()
                cursor.execute('select cname,ctime,cstart,cend,cnth,csigntime,cno from course')
                data=cursor.fetchall()
                courseSort=list()
                num=0
                for line in data:
                    name=line[0]
                    time=line[1]
                    start=line[2]
                    end=line[3]
                    cnth=line[4]
                    signstart=line[5]
                    cno=line[6]
                    if time<whatday:
                        newday=time-whatday+7
                    else:
                        newday=time-whatday
                    couorsesortalone=[newday,start,num]
                    courseSort.append(couorsesortalone)
                    num+=1
                    if signstart is not None:
                        cursor.execute("select csigntimelast from coursesign where cno='%s' and cnth='%s'"%(cno,cnth))
                        data=cursor.fetchone()
                        if not data:
                            ongoing="false"
                        else:
                            timelast=data[0]
                            signend=signstart+datetime.timedelta(minutes=timelast)
                            now=datetime.datetime.now()
                            if now>=signstart and now<=signend:
                                ongoing="true"
                            else:
                                ongoing="false"
                    else:
                        ongoing="false"
                    cursor.execute("select cno from studentapply where cno='%s' and applystate='%d'"%(cno,0))
                    applynum=len(cursor.fetchall())
                    coursealone={"name":name,"time":time,"start":start,"end":end,"ongoing":ongoing,"applynum":applynum}
                    courselist.append(coursealone)
                coursesorted=sorted(courseSort,key=itemgetter(0,1))
                coursenewlist=list()
                for ele in coursesorted:
                    index=ele[2]
                    coursenewlist.append(courselist[index])
                result={"success":"true","course":coursenewlist}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tfetchSign":
            course=postData['course']
            name=postData['name']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute("select cno from course where cname='%s'"%(course))
                cno=cursor.fetchone()[0]
                cursor.execute("select csigntime,csignposi,csigned,cnotsigned,cnth from coursesign  where cno='%s'"%(cno))
                data=cursor.fetchall()
                signedlist=list()
                for line in data:
                    signedalone={"time":line[0],"position":line[1],"signed":line[2],"notsigned":line[3],"nth":line[4]}
                    signedlist.append(signedalone)
                result={"success":"true","sign":signedlist}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result,cls=DateEncoder), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tfetchsignAlone":
            name=postData['name'] 
            course=postData['course']
            nth=postData['nth']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            studentsigned=list()
            studentall=list()
            studentnotsigned=list()
            signednumber=list()
            try:
                cursor.execute("select cno from course where cname='%s'"%(course))
                cno=cursor.fetchone()[0]
                cursor.execute("select snumber from studentsign where cno='%s' and cnth='%d'"%(cno,nth))
                data=cursor.fetchall()
                for line in data:
                    signednumber.append(line[0])
                cursor.execute("select sname,snumber from student where snumber in (select snumber from sc where cno='%s')"%(cno))
                data=cursor.fetchall()
                for line in data:
                    studentall.append([line[0],line[1]])
                for ele in studentall:
                    if ele[1] not in signednumber:
                        studentnotsigned.append(ele[0])
                    else:
                        studentsigned.append(ele[0])
                result={"success":"true","signed":studentsigned,"notsigned":studentnotsigned}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tpushSign":
            name=postData['name']
            course=postData['course']
            position=postData['position']
            last=postData['last']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                cursor.execute("select cno,cnth from course where cname='%s'"%(course))
                data=cursor.fetchone()
                cno=data[0]
                cnth=data[1]
                cnth+=1
                cursor.execute("update course set csigntime=now(),cnth='%d' where cno='%s'"%(cnth,cno))
                db.commit()
                cursor.execute("select snumber from sc where cno='%s'"%(cno))
                data=cursor.fetchall()
                if not data:
                    notsigned=0
                else:
                    notsigned=len(data)
                cursor.execute("insert into coursesign values('%s','%d',now(),'%d','%s','%d','%d')"%(cno,cnth,last,position,0,notsigned))
                db.commit()
                result={"success":"true"}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tapplylist":
            name=postData['name']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            applylist=list()
            try:
                cursor.execute("select cno,snumber from studentapply where applystate='%d'"%(0))
                data=cursor.fetchall()
                for line in data:
                    cno=line[0]
                    snumber=line[1]
                    cursor.execute("select sno,sname from student where snumber='%s'"%(snumber))
                    datai=cursor.fetchone()
                    sno=datai[0]
                    sname=datai[1]
                    cursor.execute("select cname from course where cno='%s'"%(cno))
                    cname=cursor.fetchone()[0]
                    applyalone={"name":sname,"sno":sno,"number":snumber,"course":cname}
                    applylist.append(applyalone)
                result={"success":"true","applyinfo":applylist}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="tapprovelist":
            name=postData['name']
            approvelist=postData['list']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                for ele in approvelist:
                    number=ele['number']
                    course=ele['course']
                    cursor.execute("select cno from course where cname='%s'"%(course))
                    cno=cursor.fetchone()[0]
                    cursor.execute("update studentapply set applystate='%d' where cno='%s'"%(1,cno))
                    db.commit()
                    cursor.execute("insert into sc values('%s','%s')"%(number,cno))
                    db.commit()
                result={"success":"true"}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp
        elif command=="trejectlist":
            name=postData['name']
            rejectlist=postData['list']
            db = pymysql.connect('localhost',dname, dpassword, dbase)
            cursor = db.cursor()
            try:
                for ele in rejectlist:
                    number=ele['number']
                    course=ele['course']
                    cursor.execute("select cno from course where cname='%s'"%(course))
                    cno=cursor.fetchone()[0]
                    cursor.execute("update studentapply set applystate='%d' where cno='%s'"%(2,cno))
                    db.commit()
                result={"success":"true"}
            except:
                status=400
                result={"success":"false","error":"maybe db select error"}
            db.close()
            resp = Response(json.dumps(result), status=status,
                                mimetype="application/json")
            return resp    
if __name__ == '__main__':
    app.run(host='172.16.55.156', port=80, debug=True)





            




                
                







            

