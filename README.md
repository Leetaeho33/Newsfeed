# 점메추

이 프로젝트는 Spring Security를 기반으로 한 점심 메뉴 추천 뉴스피드 기능을 구현한 프로젝트 입니다.

# ERD

프로젝트 ERD입니다. 사용자, 점심메뉴 추천 게시글과 댓글 Entity로 이루어져 있습니다. 

![newsfeed (4)](https://github.com/NBCamp-B09-Newsfeed/Backend/assets/148296128/9dd9eaad-5de7-419f-a124-a54e07fd250d)

# API 명세서

API 명세서입니다. 각 케이스별 성공 예시와 실패 예시가 있으니 같이 보시면 좋습니다.

https://documenter.getpostman.com/view/30925785/2s9YeD6s8n


# 문제점과 해결사항

1. 예외 발생시 클라이언트에게 어떤 예외가 발생했는지 알려주는 메세지와, 상태 코드를 담아서 반환 하고자 했습니다. 상태 메세지와, 코드를 담는 DTO를 CommonResponseDto를 이용해서 했습니다.
   
여기서 발생한 문제는 응답 body에 정보를 담지 않는 케이스(로그인)에서는 응답 바디에 성공/실패 여부와 상태 코드만 담아서 응답 하면 됐습니다.

하지만 응답 body에 정보를 담아서 응답 해야 할 케이스가 생겼습니다.

예를 들어 게시글 작성을 성공하면, 내가 작성한 게시글을 응답 body에 담아서 보내야 합니다. 이때는 CommonResponseDto를 사용할 수 없이, body에 정보를 넣어줄 다른 객체 예)MenuResponseDto를 이용해야 했습니다.
이렇게 코드가 구성이 되면, 실패 했을 때 상태메세지와 상태코드를 응답할 수 없었습니다.

[해결방법]
정보를 응답하는 모든 Dto에 CommonReponseDto를 상속 받게 했습니다. CommonResponseDto를 추상화하여 모든 Controller의 응답을 ResponseEntity,CommonResponseDto>로 받게 했습니다. 이렇게 코드 변경을 하니 성공햇을 때는
정보를 보내는 자식 클래스를 보내고, 실패를 했을 때는 CommonResponseDto를 응답해 실패 메세지와 코드를 반환했습니다.

[부족한 점]
성공시에도 상태메세지와 코드를 보낼 수 있으면 좋겠다고 생각했습니다. 상태코드는 ResponseEntity를 통해 할 수 있었지만, 성공 메세지를 보내는 부분이 어려웠습니다.


2. DTO 사용에 어려움이 있었습니다. 요청마다 필요한 정보가 달라서 요청에 맞는 DTO를 만들어줬습니다. 이렇게 생긴 DTO들이 나중에는 너무 많이 생겨서 관리에 어려움이 있었습니다.

예를 들어 로그인 요청 DTO와 비밀번호 확인 DTO, 회원수정 요청 DTO가 다 따로 있어야 했습니다. 작은 프로젝트에도 DTO가 너무 많이 늘어났다고 생각했습니다.


3. 아직 모든 도메인의 Test를 작성하진 못했습니다. User의 Entity, Menu 도메인의 Repository, Service에 대한 테스트를 작성했습니다.

4. 테스트를 구현하면서 어려웠던 점입니다. Service에서 테스트를 진행할 때 RequestDto에서 Entity로 객체를 초기화 할 때 어려움이 있었습니다. RequestDto에는 @Setter어노테이션도, 생성자도 없었습니다. 왜냐하면 api 요청시 body에 JSON형태로 데이터를 보내면, RequestDto로 @RequestBody 어노테이션으로 객체로 맵핑이 자동으로 되기 때문으로 알고 있습니다. 하지만 test에서는 @Setter도, 생성자도 없이 RequestDto를 초기화 시키는 방법이 없습니다.
이에 테스트를 위해 개발 코드를 변경해야 하는가?라고 하면 저는 바뀌어서는 안된다고 생각했습니다. 처음으로 사용 했던 방법은 String 데이터 타입으로 JSON형태의 데이터를 만들고, ObjectMapper를 통해 JSON데이터를 객체에 맵핑을 하려고 했습니다. 하지만 String 형식의 JSON 데이터는 자바에서 사용하는게 어려워서 구현에 실패했습니다.
[해결방법]
하는 수 없이 RequestDto에 @Setter 어노테이션을 달고, set메서드를 사용했습니다. 테스트를 위해 본코드를 변경해야했지만, @Setter어노테이션 한개만 달아주면 테스트에 편의성이 생긴다고 생각했습니다. RequestDto에 @Setter가 필요는 없지만 그렇다고 있으면 안된다고 생각하지도 않았습니다. 
