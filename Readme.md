# 📌Didabara

> ## **기업,동아리 또는 스터디 그룹을 위한 업무/정보 제공 및 운영 관리 서비스입니다.**
>
> **초대코드를 통해 커뮤니티를 구독**
>
> **구독시 해당 커뮤니티에 업로드된 정보 제공**
>
> **구독 리스트를 통해 내가 참여하고 있는 커뮤니티 관리**`
>
> **ppt, excel, docx, jpg 등 의 파일 업로드, 미리보기,다운로드 기능**
>
> **같은 커뮤니티를 구독 하고 있는 사용자들만의 채팅,댓글 기능**

# 1.제작기간 & 참여인원

- 2022.08.16~ 2022.09.16
- 백엔드 3, 프론트엔트 2

# 2.주요 기술 스택

> Bake-end

- Java
- Spring boot
- Gradle
- Spring Data JPA
- MySQL
- Spring Security
- JWT token
- Stomp

> front-end

- React
- MUI
- StompJs
- SockJs

# 3.서비스 소개 및 주요 기능

## 3.1 메인

![image](https://user-images.githubusercontent.com/104490310/192136242-26e2f247-e951-4487-903f-eb4b69c36d6d.png)

## 3.2 커뮤니티생성하기,커뮤니티 목록

### 커뮤니티 목록

![image](https://user-images.githubusercontent.com/104490310/192136250-f425204e-3702-4c37-b889-d7fb7a0bcfed.png)

### 커뮤니티 생성

![화면 캡처 2022-09-25 181006](https://user-images.githubusercontent.com/104490310/192136370-98b0f000-5863-4796-a802-734fef79345a.png)

### 커뮤니티 글 생성

![화면 캡처 2022-09-25 181220](https://user-images.githubusercontent.com/104490310/192136407-d9ff7b6f-608e-4871-8fe4-132984f07a89.png)

## 3.3 구독하기,구독목록

![화면 캡처 2022-09-25 181408](https://user-images.githubusercontent.com/104490310/192136820-804119b0-9d8e-4033-ad2e-4681314621c2.png)

### 초대코드를 입력시 해당 커미뉴티가 내 구독 리스트에 생성

![화면 캡처 2022-09-25 182625](https://user-images.githubusercontent.com/104490310/192136937-10063713-b955-44f4-8073-727e73a6bcd3.png)
![화면 캡처 2022-09-25 182705](https://user-images.githubusercontent.com/104490310/192136948-a1d3bcbf-349c-4f65-8d71-9f8796485155.png)

## 3.4 채팅기능 && 댓글 기능

### 해당 커뮤니티를 구독하고있는 사용자간에 채팅 기능 활성화

<img src= "https://user-images.githubusercontent.com/104490310/192137024-f946af1a-0e7c-4e0c-bc73-f8f45627956d.png" height="10%" width="30%">

### 커뮤니티 글 마다 댓글 작성/수정/삭제

![화면 캡처 2022-09-25 225637](https://user-images.githubusercontent.com/104490310/192147413-25134da2-0d40-4590-bb20-cc75fe0bcca6.png)

## 3.5 마이페이지

### MUI를 활용한 UI

![화면 캡처 2022-09-25 183308](https://user-images.githubusercontent.com/104490310/192137128-ec7e61b6-8f31-4a17-be48-ebb1e8d5b0a4.png)

# 4.ERD 설계

![erd](https://user-images.githubusercontent.com/104490310/191962636-01440206-4a7f-47b4-824a-a47e2d2b3729.png)

# 5.트러블 슈팅

## 소셜로그인

...

## s3업로드,RequestPart

...

## Stompjs/SockJs

...

# 6.회고/느낀점

-좋았던점-

웹 개발 첫 프로젝트였지만 로그인 하나만큼은 제대로 해보고싶은 마음이 컸다.그래서 소셜로그인도 구현해보고 이메일 인증,휴대폰 인증까지 모두 구현해봤다 . 내 코드가 좋은 코드는 아니겠지만 아직 좋은 코드를 작성할수있는 실력도 안되고 개발기간이 짧아 구현했다는거 자체에 의미를 두면서 프로젝트를 진행했던거같다.
사용할줄도 모르는 API를 가져와 사용해보고, S3에 내 버킷을 만들어 파일을 업로드해보고 , 업로드한 파일을 가져다 지워보고 수정도 해보고 라이브러리를 끌고와서 pdf로 변환해가는 과정들 하나하나가 신기하고 재밌었다.

-아쉬웠던점-

OAuth의 원리에 대해 충분히 공부하고 코드를 작성했다면, 매일매일 코드 리뷰 시간을 가져 서로의 진행상황을 공유하여 부족한 부분을 도와주었다면 조금더 좋은 결과물이 나왔을거같다.

-?다짐?-

HTTP 웹 기본 지식을 조금 더 공부하는 시간이 있으면 좋을거같다.

Clean Code에 대해 공부를 해야할거같다
