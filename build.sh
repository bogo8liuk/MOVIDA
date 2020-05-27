#!/usr/bin/env bash

set -e

movida=0

for file in *
do
    if [ -d ${file} ]
    then
        if [ ${file} == "movida" ]
        then
            movida=1
            break
        fi
    fi
done

if [ $((movida)) -eq 0 ]
then
    return
fi

path="$(dirname `realpath "${BASH_SOURCE[0]}"`)"

function undo_build {
    read -p "You are deleting all the compiled files. Are you sure to continue [Y/n]?" user_in
    case "${user_in}" in
        "Y" | "y" | "\n")
        rm ${path}/movida/borghicremona/*.class
        rm ${path}/movida/commons/*.class
        ;;

        "N" | "n")
        return
        ;;

        *)
        undo_build
        ;;
    esac
}

case "$1" in
    "-b")
    javac ${path}/movida/commons/*.java
    javac ${path}/movida/borghicremona/*.java
    ;;

    "-u")
    undo_build
    ;;        

    *)
    javac ${path}/movida/commons/*.java
    javac ${path}/movida/borghicremona/*.java
    ;;
esac
