## memcache client using spymemcached


### download memcached :
wget https://memcached.org/latest</br>
tar -zxf memcached-1.5.4.tar.gz

### install libevent:
brew install libevent

### compile memcached:
sudo ./configure --with-libevent=/usr/local/Cellar/libevent/2.1.8 --enable-sasl-pwdb </br>
sudo make && make test && sudo make install

### run:
memcached start


