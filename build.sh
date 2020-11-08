#!/usr/bin/env bash

set -e

path="$(dirname `realpath "${BASH_SOURCE[0]}"`)"
movida_path="${path}/movida"
bc_path="${path}/movida/borghicremona"
commons_path="${path}/movida/commons"
hashmap_path="${path}/movida/borghicremona/hashmap"
graph_path="${path}/movida/borghicremona/graph"


function undo_build {
    read -p "You are deleting all the compiled files. Are you sure to continue [Y/n]? " -n 1 user_in
    case "${user_in}" in
        "Y" | "y" | "")
        echo ""
        rm ${hashmap_path}/*.class
        rm ${bc_path}/*.class
        rm ${commons_path}/*.class
		rm ${graph_path}/*.class
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
    "-b" | "-b=all")
    javac ${hashmap_path}/*.java
    javac ${bc_path}/*.java
    javac ${commons_path}/*.java
    ;;

    "-b=borghicremona")
    javac ${hashmap_path}/*.java
    javac ${bc_path}/*.java
    ;;

    "-b=commons")
    javac ${commons_path}/*.java
    ;;

    "-b=hashmap")
    javac ${hashmap_path}/*.java        # This will cause the compilation of some files in "movida/borghicremona/" path, because of dependencies
    ;;

    "-b=graph")
    javac ${graph_path}/*.java
    ;;

    "-u")
    set +e
    undo_build
    set -e
    ;;        

    *)
    echo "Empty or invalid option"
    ;;
esac
