import SockJS from "sockjs-client";
import Stomp from 'stompjs'
import {STOMP_ENDPOINT} from "./constants/constants";

const TIMEOUT = 15000;

class wsClient {
    static stompClient;
    static registrations = []

    constructor() {
        if (!wsClient.stompClient) {
            const sock = new SockJS(STOMP_ENDPOINT, null, {timeout: TIMEOUT});
            wsClient.stompClient = Stomp.over(sock);
        }
    }

    subscribe(url, callback) {
        if (wsClient.stompClient.connected) {
            wsClient.stompClient.subscribe(url, callback);
        } else {
            wsClient.registrations.push({url: url, callback: callback});
        }
    }

    connect() {
        wsClient.stompClient.connect({}, function () {
            wsClient.registrations.forEach(function (registration) {
                wsClient.stompClient.subscribe(registration.url, registration.callback);
            });

            wsClient.registrations.length = 0;
        }).bind(this);
    }
}

export default wsClient;