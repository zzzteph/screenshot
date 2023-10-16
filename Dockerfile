FROM alpine:latest


#packages installing
RUN apk -U upgrade --no-cache \
    && apk add --no-cache autoconf bind-tools ca-certificates chromium chromium-chromedriver git imagemagick libffi-dev libgcc libpcap libpcap-dev libssl3 libstdc++ libtool linux-headers lua-dev maven musl-dev openjdk17 openssl-dev


RUN git clone https://github.com/zzzteph/screenshot /screenshot
RUN cd /screenshot && mvn package
RUN mv /screenshot/target/screenshot-1.0.jar screenshot.jar
WORKDIR /

ENTRYPOINT ["java","-jar","screenshot.jar"]
