# NAP Tracker Android Quickstart

NAP Tracker SDK를 **앱에 연동하고 동작을 빠르게 확인**할 수 있는 Android 샘플 앱입니다.

## 실행 방법

1. Android Studio로 프로젝트를 엽니다.
2. 앱을 실행합니다.

## 앱 구성(화면)

상단 탭으로 구성되어 있습니다.

### Quick start

처음 사용자를 위한 **최소 플로우**입니다.

- SDK 초기화 확인: `NapTrackerSampleApplication`의 `Application.onCreate()`에서 초기화
- 첫 이벤트 전송: `sample_event` 전송
- 자동 `screen_view` 확인: `DetailActivity` 열기

### Examples

기능별 **예제/테스트 화면**입니다.

- 커스텀 이벤트: `NapTracker.logEvent(...)` (이벤트명 + key/value 파라미터)
- 사용자 식별/속성: `NapTracker.setUserId(...)`, `NapTracker.setUserProperty(...)`
- 자동 `screen_view`: `DetailActivity` 열기
- UI Logs: 화면에서 수행한 호출을 간단히 표시

## SDK 의존성(배포 지원)

샘플 앱은 기본적으로 Maven Central에 배포된 SDK를 사용합니다.

- `app/build.gradle.kts`:
  - `implementation("io.github.nasmedia-tech:nap-tracker:0.0.5")`

## 주요 코드

- `app/src/main/java/com/nasmedia/naptracker/sample/NapTrackerSampleApplication.kt`: SDK 초기화
- `app/src/main/java/com/nasmedia/naptracker/sample/MainActivity.kt`: Compose 엔트리
- `app/src/main/java/com/nasmedia/naptracker/sample/ui/NapTrackerSampleApp.kt`: 탭 UI(Quick start / Examples)
