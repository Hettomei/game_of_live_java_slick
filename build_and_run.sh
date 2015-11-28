#!/bin/bash

mkdir build
echo "Compiling"
javac -cp "./lib/*:." jdlv/StartJDLV.java -d build -encoding UTF8

echo "run"
cd build
java -cp "../lib/*:." -Djava.library.path=../lib jdlv.StartJDLV
