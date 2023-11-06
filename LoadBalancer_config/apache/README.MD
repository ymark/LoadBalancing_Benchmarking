1. Install Apache2 from apt
```
sudo apt install apache2
```
2. Enable Apache2 modules for proxy and load balancing
```
sudo a2enmod proxy
sudo a2enmod proxy_http
sudo a2enmod proxy_balancer
sudo a2enmod lbmethod_byrequests
```
3. Restart Apache2 
```
sudo systemctl restart apache2
```
4. Configure Proxy server. A sample is shown below (```/etc/apache2/sites-enabled/000-default.conf```)
```
<VirtualHost *:80>
        ServerName 1.2.3.4
        ServerAdmin marketak@ics.forth.gr
        DocumentRoot /var/www/html

        ErrorLog ${APACHE_LOG_DIR}/error.log
        CustomLog ${APACHE_LOG_DIR}/access.log combined

        ProxyPreserveHost On
		ProxyPass / http://5.6.7.8:8080/
		ProxyPassReverse / http://5.6.7.8:8080/

</VirtualHost>
```
5. Configure load balancer. A sample is shown below (```/etc/apache2/sites-enabled/000-default.conf```)
```
<VirtualHost *:80>
		ServerName 1.2.3.4
        ServerAdmin marketak@ics.forth.gr
        DocumentRoot /var/www/html
		<Proxy balancer://mycluster>
			BalancerMember http://127.0.0.1:8080
			BalancerMember http://127.0.0.1:8081
		</Proxy>

		ProxyPreserveHost On

		ProxyPass / balancer://mycluster/
		ProxyPassReverse / balancer://mycluster/
</VirtualHost>
```