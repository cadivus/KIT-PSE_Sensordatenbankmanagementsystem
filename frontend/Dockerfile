FROM tiangolo/node-frontend:10 as build-stage
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY ./ /app/
RUN npm run build


FROM nginx:1-alpine
COPY --from=build-stage /app/build/ /usr/share/nginx/html
COPY --from=build-stage /app/deploy/run_nginx_config.sh /bin/run_nginx_config.sh

RUN chmod +x /bin/run_nginx_config.sh
CMD ["sh", "/bin/run_nginx_config.sh"]

