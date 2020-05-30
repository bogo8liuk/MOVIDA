#!/usr/bin/env bash

set -e

path="$(dirname `realpath "${BASH_SOURCE[0]}"`)"

function undo_build {
    read -p "You are deleting all the compiled files. Are you sure to continue [Y/n]? " -n 1 user_in
    case "${user_in}" in
        "Y" | "y" | "")
        echo ""
        rm ${path}/movida/borghicremona/*.class
        rm ${path}/movida/commons/*.class
        ;;

        "N" | "n")
        echo ""
        return
        ;;

        *)
        echo ""
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
