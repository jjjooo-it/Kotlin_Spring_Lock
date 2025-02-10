import http from 'k6/http';
import { check, sleep } from 'k6';

const memberId = 1;
export default function () {
    const res = http.post(`http://localhost:8080/api/coupon/issue`,
        JSON.stringify({ memberId: memberId }), {
        headers: { 'Content-Type': 'application/json' },
    });git
    sleep(1);
}
