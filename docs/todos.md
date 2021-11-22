# step4 

- [x] service에서 dto로 반환하도록 변경
- [x] 패키지 분리
---
### 의존성 그래프 작성
![의존성 그래프 작성](./image/리팩터링미션_의존성그래프.jpg)
---
### 패키지 분리
- [x] Price: element 패키지를 만들어 분리
- [x] Event
  - Product 패키지로 이동
  - handle repository에 따라 분리

### 패키지간 단방향 설정
- [x] TableGroup 리스트 반환 제거하는 방향으로 수정
- [x] OrderStatus validate 로직 DI 방식으로 의존성 역전 설정
  - TableGroup
  - Table(OrderTable)

### 클래스간 단방향 설정
- [x] Order. List<OrderLineItem> -> OneToMany 단방향 설정
- [x] Menu. List<MenuProduct> -> OneToMany 단방향 설정
---
- [x] step3에서 order과 menu 사이의 관계 미션 구현 
  -> 어떻게 관계를 끊어주는지 모르겠다. Price~Menu 사이의 가격 변동을 동기화가 되어있는 상태이다.
     OrderLineItem에서 그 당시의 menu 정보를 같이 넣어주는 것 이외에는 그 괸계를 어떻게 끊어주는지 잘 모르겠다.
- [x] 멀티모듈 내용 프로젝트에 적용(step4)
