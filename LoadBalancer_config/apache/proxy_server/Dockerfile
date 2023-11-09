FROM ubuntu/apache2:2.4-21.04_edge

RUN a2enmod proxy

RUN a2enmod proxy_http

RUN a2enmod proxy_balancer

RUN a2enmod lbmethod_byrequests

RUN service apache2 restart
