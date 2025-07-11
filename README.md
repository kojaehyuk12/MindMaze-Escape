# 🗝️ [게임 제목] - 몰입형 Android 방탈출 게임

## ✨ 프로젝트 개요

본 프로젝트는 Android Studio와 Java를 사용하여 개발된 모바일 방탈출 게임입니다. 플레이어는 다양한 수수께끼와 퍼즐로 가득 찬 방에서 단서를 찾아내고, 아이템을 활용하며, 논리적인 사고를 통해 잠금을 해제하고 탈출해야 합니다. 각 챕터는 독특한 테마와 도전 과제를 제공하며, 플레이어의 선택과 퍼즐 해결 방식에 따라 여러 엔딩을 경험할 수 있습니다.

**[여기에 게임의 구체적인 스토리나 컨셉을 한두 문장으로 추가해주세요. 예: "어둠 속에 갇힌 저택에서 기억을 잃은 주인공이 되어, 숨겨진 진실을 파헤치고 탈출해야 합니다."]**

## 🛠️ 주요 기능

* **챕터 기반 진행:** 여러 챕터로 구성되어 있어, 각 챕터마다 새로운 환경과 퍼즐이 제공됩니다.
* **다양한 퍼즐 유형:** 
    * **논리 퍼즐:** 체스판 문제 (N-Queens 변형), 숫자 31 게임 등 논리적 사고를 요구하는 퍼즐.
    * **아이템 활용 퍼즐:** 숨겨진 아이템을 찾아 인벤토리에 보관하고, 올바른 상황에 사용하여 새로운 길을 열거나 단서를 얻습니다.
    * **관찰 및 입력 퍼즐:** 주변 환경을 면밀히 관찰하여 단서를 얻고, 특정 코드나 텍스트를 입력하여 해결하는 퍼즐 (예: Keypad, Listening, Pentagon).
    * **인터랙티브 오브젝트:** 드래그 앤 드롭과 같은 상호작용을 통해 해결하는 퍼즐 (예: 체스 기물 이동).
* **인벤토리 시스템:** 획득한 아이템을 관리하고 필요한 시점에 사용할 수 있는 인벤토리 기능을 제공합니다.
* **다중 엔딩:** 플레이어의 선택이나 게임 진행 상황에 따라 다른 결말(Ending 1, Ending 2)을 맞이할 수 있습니다.
* **타이머 시스템:** 일부 챕터에서는 시간 제한이 있어 더욱 긴장감 넘치는 플레이를 유도합니다.
* **오디오 효과:** 몰입감을 높이는 배경 음악(BGM)과 퍼즐 해결 시 재생되는 효과음(SFX)을 포함합니다.
* **풀스크린 몰입 모드:** 게임 플레이 중 시스템 UI(내비게이션 바, 상태 바)를 숨겨 게임에 완전히 집중할 수 있도록 합니다.

## ⚙️ 기술 스택

* **플랫폼:** Android
* **개발 언어:** Java
* **개발 환경:** Android Studio
* **주요 Android 컴포넌트 및 API:**
    * `AppCompatActivity`: 각 게임 화면 및 퍼즐을 구현하는 액티비티.
    * `MediaPlayer`: 배경 음악 및 효과음 재생 관리.
    * `CountDownTimer`: 시간 제한이 있는 퍼즐 구현.
    * `ImageView`, `Button`, `EditText`, `TextView`: 사용자 인터페이스 구성.
    * `GridView`, `BaseAdapter` (`BagImageAdapter`): 인벤토리 시스템 구현.
    * `GridLayout`: 체스판과 같은 그리드 기반 퍼즐 레이아웃.
    * `DragEvent`, `ClipData`: 드래그 앤 드롭 상호작용.
    * `Intent`, `Bundle`: 액티비티 간 데이터 전달 및 화면 전환.
    * `Toast`: 사용자에게 메시지 피드백 제공.
    * `HashSet`, `ArrayList`, `HashMap`: 아이템 관리 및 퍼즐 데이터 구조.
    * `Random`: 31 게임과 같은 무작위 요소 구현.

## 🚀 실행 방법

본 프로젝트는 Android Studio 환경에서 실행할 수 있습니다.
  **Android Studio에서 프로젝트 열기:**
    * Android Studio를 실행하고, `File` > `Open`을 클릭한 다음, 클론한 프로젝트 폴더를 선택하여 엽니다.
   **빌드 및 실행:**
    * Gradle 동기화가 완료되면, Android 에뮬레이터 또는 실제 Android 기기를 연결한 후 `Run 'app'` 버튼 (초록색 재생 버튼)을 클릭하여 앱을 빌드하고 실행합니다.

## 🎮 게임 플레이 방법

1.  **메인 화면:** 앱을 시작하면 게임의 메인 화면이 나타납니다.
2.  **챕터 시작:** 챕터 선택 또는 게임 시작 버튼을 눌러 첫 번째 챕터로 진입합니다.
3.  **탐색 및 상호작용:** 화면을 터치하여 오브젝트와 상호작용하고, 숨겨진 단서를 찾습니다.
4.  **아이템 획득 및 사용:** 획득한 아이템은 인벤토리에 보관되며, 적절한 퍼즐에서 아이템을 사용해야 진행할 수 있습니다.
5.  **퍼즐 해결:** 각 챕터의 특정 구역에는 다양한 유형의 퍼즐이 있습니다. 단서를 조합하고 논리적으로 생각하여 퍼즐을 해결하세요.
6.  **다음 챕터로:** 현재 챕터의 모든 퍼즐을 해결하면 다음 챕터로 이동할 수 있습니다.
7.  **엔딩:** 모든 챕터를 클리어하면 게임의 엔딩을 볼 수 있습니다. (플레이 방식에 따라 다른 엔딩이 나타날 수 있습니다.)

## 💡 개발 하이라이트 및 트러블슈팅

* **몰입형 UI 구현:** `setStickyImmersiveMode()` 메서드를 활용하여 게임 플레이 중 시스템 UI(네비게이션 바, 상태 바)를 숨겨 플레이어가 게임에 완전히 몰입할 수 있도록 구현했습니다.
* **다양한 오디오 관리:** `MediaPlayer`를 사용하여 배경 음악(BGM)과 퍼즐 해결 시 필요한 효과음(SFX)을 적절히 재생하고 관리하여 게임의 분위기와 피드백을 강화했습니다. `onPause()`, `onResume()`, `onDestroy()` 라이프사이클 메서드에서 오디오를 효율적으로 제어했습니다.
* **드래그 앤 드롭 퍼즐:** 체스판 퍼즐에서 `ClipData`, `DragEvent`, `View.OnDragListener` 등을 활용하여 직관적인 드래그 앤 드롭 상호작용을 구현했습니다. 이를 통해 플레이어가 직접 기물을 움직여 퍼즐을 해결하는 재미를 제공했습니다.
* **멀티 엔딩 구현:** 게임 진행 중 특정 조건이나 플레이어의 선택에 따라 `Ending1`과 `Ending2`로 분기되는 로직을 구현하여 게임의 재플레이 가치를 높였습니다.