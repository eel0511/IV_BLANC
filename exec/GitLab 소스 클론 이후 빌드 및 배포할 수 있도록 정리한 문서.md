# GitLab 소스 클론 이후 빌드 및 배포할 수 있도록 정리한 문서

## 1. 프로젝트 개발 설정 값 및 버전

### Server
---
java 1.8 
mysql (springboot연결은 mariadb)
Intellij Ultimate 2021.3.1

Python=3.8 pytorch=[>=1.6.0] cudatoolkit=[>=9.2]
mysql(flask연결은 pymysql)


---
### Web

---

node.js 16.13.1

- 크롬의 경우 https://chrome.google.com/webstore/detail/allow-cors-access-control/lhobafahddgcelffkeicbaginigeejlf?hl=ko  CORS 확장 프로그램 설치 필수


---

## 2. 빌드 시 사용되는 환경 변수 등의 주요 내용 상세 기재

### spring boot

#### application-alpha.yml 

1. datasource의 데이터베이스 연결

2. server의 포트, 도메인 설정

3. Swagger 호스트 설정
4. firebase-config에 friebase json파일 위치를 입력
5. 기타 필요한 설정

#### ClothesController 

sendurl은 일반 플라스크 서버의 주소:포트

sendAiurl은 ai처리하는 플라스크 서버의 주소:포트

#### FileService

DOWNLOAD_URL = firebase에 올라간 사진이 저장되는 GCP경로

bucket이름 등 firebase 관련 설정 필요

#### FcmService

API_URL 등 fcm 관련 설정 필요

### Flask 서버

---
#### 배경제거 플라스크 서버

myflask안의 main.py(spring의 /api/clothes/add 에 쓰임 )

1. 데이터베이스 설정
2. Firebase 설정


#### 가상피팅 플라스크 서버(GPU)

flask_AI 안의 app.py (스프링의 /api/clothes/beta 에 쓰임)


1. Firebase 설정

2. VITON-HD 설정

https://github.com/shadow2496/VITON-HD를 클론하여 Installation 과정을 거치고
그 안에 flask_AI 의 app.py를 위치시키고(파이어베이스 json도 위치) 
test.py 는 flask_AI의 test.py 를 덮어씌워줍니다

---
### 서버 구동

springboot 서버 로컬에서 열 경우  Gradle의 cleanQuerydslSourcesDir , bootRun 차례대로 실행해줍니다

springboot 서버와 myflask안의 Flask서버는 aws에서 빌드 할 경우

#### jenkins
i6d104.p.ssafy.io:9090
ivblanc // ivblanc
test 파이프 라인 실행시 빌드되고 jar파일이 나옵니다

실행은
i6d104.p.ssafy.io AWS 에서 sudo su후 있는 start.sh web.sh 실행으로 할 수 있습니다



## Web

로컬에서 실행할 경우

```
cd web
npm i
npm start
```

로컬에서 실행하는 경우 로컬호스트 주소가 서버 도메인과 달라 **JWT 토큰이 받아지지 않습니다.**

회원가입/로그인은 되나 옷 등록 등 파일 업로드 및 받아오기가 **<u>서버와 연동되지 않습니다.</u>**

젠킨스에서 test 파이프라인 실행 시 빌드가 됩니다.

실행은
i6d104.p.ssafy.io AWS 에서 sudo su -> cd -> bash web.sh를 통해 실행할 수 있습니다.

http://i6d104.p.ssafy.io/ 해당 주소로 들어가서 웹을 실행할 수 있습니다.



## 3. 배포 시 특이사항

AI 플라스크 서버를 돌리기위해 GPU가 필요합니다.

## 4. DB접속 정보 등 프로젝트에 활용되는 주요 계정 및 프로퍼티가 정의된 파일 목록

Mysql
id: ivblanc
pw: ivblancgumi104

# 프로젝트에서 사용하는 외부 서비스 정보

firebase json파일은 모두 서버파일에 들어있습니다( iv-blanc-firebase-adminsdk-l6zca-232ca5420a.json )

Firebase Storage, Firebase cloud Message
Naver Map, 공공데이터 날씨API

# DB덤프 파일 최신본

ivblancdump.sql

###서비스 이용가능 계정

ms001118@naver.com
12345678a

ms001119@naver.com
12345678a

ms001120@naver.com
12345678a

ms001121@naver.com
12345678a



