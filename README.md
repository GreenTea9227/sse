# 기본 sse 연결

[sse 연결]            
- sse 연결을 하는 코드입니다.

## 1. sse 연결

```javascript
function connectSse() {
        // SSE 연결을 설정하는 함수
        const sse = new EventSource("/sse/connect");

        // SSE 연결이 열리면 실행될 콜백 함수
        sse.onopen = function () {
            console.log("SSE 연결 완료");
        };

        // SSE 연결이 끊어지면 실행될 콜백 함수
        sse.onclose = function (event) {
            console.log("SSE 연결이 끊어졌습니다.");

            // 일정 시간 후에 다시 연결을 시도하는 함수 호출
            setTimeout(function () {
                console.log("SSE 연결을 다시 시도합니다.");
                connectSse(); // SSE 연결 다시 설정
            }, 3000); // 3초 후에 다시 연결 시도
        };
    }
```
- sse.onopen을 통해 연결 완료시 콘솔에 출력
- sse.onclose를 통해 연결이 끊어지면 콜백형태로 다시 연결을 시도


## 2. 서버에서 sseemitter 생성
```java
 SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        sseEmitters.add(emitter);
```
- sse연결 요청이 오면 sseemitter를 생성하여 메모리에 저장



## 3. 서버에서 이벤트 송신
```java
 emitters.forEach(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)
                                .data(data)
                );
            } catch (ClientAbortException e) {

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
```

- 메모리에 저장된 emiiters를 통해 모든 emitter에 이벤트를 발생하도록 



## 4. sse 이벤트 수신 

```javascript
sse.addEventListener('chat__messageAdded', () => {
        Chat__loadMore();
    });
```
- 서버에서 이벤트가 발생하면 위 함수가 받아서 html에 데이터 출력




