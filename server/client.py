import datetime
import json
import time

import requests

address="http://127.0.0.1:8200"

def slogin(number,password):
    user_info = {'type': 'slogin', 'number':number,'password':password}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)

def sregister(name,sex,school,sid,number,password):
    user_info = {'type': 'sregister','name':name,'sex':sex,'school':school,'sid':sid,'number':number,'password':password}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)

def sfetchCourse(number):
    user_info = {'type': 'sfetchCourse','number':number}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def saddCourse(number,course,teacher):
    user_info = {'type': 'saddCourse','number':number,'course':course,'teacher':teacher}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
    # still thinking
def sfetchSign(course):
    user_info = {'type': 'sfetchSign','course':course}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
    #still thinking
def ssign(snumber,course):
    user_info = {'type': 'ssign','snumber':snumber,'course':course}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
    # not test yet
def tlogin(name,password):
    user_info = {'type':'tlogin','name':name,'password':password}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)

def tfetchCourse(name):
    user_info = {'type': 'tfetchCourse','name':name}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def tfetchSign(course,name):
    user_info = {'type': 'tfetchSign','course':course,'name':name}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def tfetchsignAlone(name,course,nth):
    user_info = {'type': 'tfetchsignAlone','name':name,'course':course,'nth':nth}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def tpushSign(name,course,position,last):
    user_info = {'type': 'tpushSign','name':name,'course':course,'position':position,'last':last}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def tapplylist(name):
    user_info = {'type': 'tapplylist','name':name}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def tapprovelist(name,approvelist):
    user_info = {'type': 'tapprovelist','name':name,'list':approvelist}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)
def trejectlist(name,rejectlist):
    user_info = {'type': 'trejectlist','name':name,'list':rejectlist}
    r = requests.post(address,data=json.dumps(user_info))
    print(r.text)

# sregister('1',0,'zjgsu','190201','000001','123456')
# sregister('2',1,'zjgsu','190202','000002','123456')
# slogin('000001','123456')
# sfetchCourse('000001')
# saddCourse('000002','test2','hasha')

# tfetchCourse('hasha')
# tpushSign('hasha','test1','a101',10)
# tfetchSign('test1','hasha')
# tfetchsignAlone('hasha','test1',11)
# tapplylist('hasha')
# tapprovelist('hasha',[{"number":'000002','course':'test1'}])
# trejectlist('hasha',[{"number":"000002","course":"test2"}])


sregister('3',1,'zjgsu','190203','000003','123456')




