car_func		클라이언트 기능 액티비티
car_func_aircon	에어컨 프래그먼트 (뷰페이저)
car_func_home	뷰페이저(에어컨 등 추천 기능), 오디오, 비디오 포함한 프래그먼트
car_func_mycar	차 권한 관리 프래그먼트 (listView -> 아직 오류가 있어용..)
car_func_setting	환경설정 프래그먼트
car_func_user	사용자 프로필 관리 프래그먼트
Fragment1	다른 추천 기능(미정). 뷰페이저가 잘 작동하는지 확인용
ListViewAdapter, ListViewItem	차 권한 관리 프래그먼트(car_func_mycar)에 올라갈 리스트뷰
MainActivity	처음 화면
SignIn		로그인
SignUp		회원가입 (이번주 내로 만들어서 수정할게요!!)
ViewPagerFragmentAdapter 		car_func_home에 올라가는 뷰페이저. 추천 기능용.


실행 순서
MainActivity
-> Sign In 로그인 버튼 클릭
-> car_func위에 car_func_home 프래그먼트가 생성
-> 내비게이션 뷰로 홈, 사용자, 차량 관리, 환경설정으로 넘어갈 수 있음
-> 홈(car_func_home), 사용자(car_func_user), 차량 관리(car_func_mycar), 환경설정(car_func_setting)
	-> 홈은 car_func_aircon, Fragment1을 포함한 뷰페이저와 오디오, 비디오 이미지 버튼 포함
	-> 차량 관리는 listView를 포함할 예정. 아직 미구현
