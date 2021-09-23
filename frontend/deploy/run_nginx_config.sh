#!/bin/sh

API_BACKEND_URL=${API_BACKEND_URL:-'http://backend:8081'}
API_NOTIFICATION_URL=${API_NOTIFICATION_URL:-'http://notification:8080'}

conf_file="/etc/nginx/conf.d/default.conf"

echo $'server {
  listen 80;
  include /etc/nginx/mime.types;
  root /var/www;
  index index.html index.htm;
  location /api/backend {
    rewrite /api/backend/(.*) /$1  break;
    rewrite /api/backend /  break;' > $conf_file
echo "    proxy_pass         ${API_BACKEND_URL}; " >> $conf_file
echo $'    proxy_redirect     off;
    proxy_set_header   Host $host;
  }
  
  location /api/notification {
    rewrite /api/notification/(.*) /$1  break;
    rewrite /api/notification /  break;' >> $conf_file

echo "    proxy_pass         ${API_NOTIFICATION_URL}; " >> $conf_file
echo $'    proxy_redirect     off;
    proxy_set_header   Host $host;
  }
  location / {
    root /usr/share/nginx/html;
    index index.html index.htm;
    try_files $uri $uri/ /index.html =404;
  }
}' >> $conf_file

nginx -g "daemon off;"
