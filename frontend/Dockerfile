# Используем официальный образ Node.js
FROM node:22-alpine AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем все файлы приложения
COPY . .

# Собираем приложение
RUN npm run build

# Используем Nginx для раздачи статических файлов
FROM nginx:alpine

# Копируем собранные файлы в Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Открываем порт 80
EXPOSE 8080 5500

# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]