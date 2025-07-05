## Запуск приложения
Запуск производится с помощью Docker Compose

docker-compose up -d

Для совместимости с FRONT приложение работает на порту 5500

http://localhost:5500/

## Пример запросов
Пример POST запроса на инициализацию трансфера денежных средств

Request URL: http://localhost:5500/transfer
Accept: application/json, text/plain
Content-Type: application/json;charset=UTF-8
Request Payload :
{
  “cardFromNumber”: “2222222222222222”,
  “cardToNumber”: “2222222222222222”,
  “cardFromCVV”: “232”,
  “cardFromValidTill”: “12/23”,
  “amount”: {
  “currency”: “RUR”,
  “value”: 22200
  }
}
Response:
{
  “operationId”:“1”
}

Пример POST запроса на инициализацию трансфера денежных средств

Request URL: http://localhost:5500/confirmOperation
Accept: application/json, text/plain
Content-Type: application/json;charset=UTF-8
Request Payload :
{
  “code”: “0000”,
  “operationId”: “1”
}
Response:
{
  “operationId”:“1”
}