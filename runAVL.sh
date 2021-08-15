#!/bin/bash
if [ $# -eq 0 ]; then
    echo "Provide input on console"
    java DriverAVL
fi
if [ $# -eq 1 ]; then
    java DriverAVL < "${1}"
fi
if [ $# -eq 2 ]; then
    java DriverAVL < "${1}" > "${2}"
fi

