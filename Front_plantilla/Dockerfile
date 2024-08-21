# Etapa de construcción
FROM node:lts-bullseye AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build --prod

# Etapa de producción
FROM nginx:alpine
# Copiar el archivo de configuración de Nginx desde la carpeta config
COPY config/nginx.conf /etc/nginx/nginx.conf
# Copiar los archivos de build desde el contenedor anterior al contenedor de Nginx
COPY --from=build /app/dist/Front_plantilla /var/www/app/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]




