/**
 * Created by Administrator on 2016-05-05.
 */
var stompClient = null;

function connect() {
    var socket = new SockJS('/hotel');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            console.log(JSON.parse(greeting.body));
        });
    });
}

connect();