#!/usr/bin/env bash

set -e

path="$(dirname `realpath "${BASH_SOURCE[0]}"`)"
movida_path="${path}/movida"
bc_path="${movida_path}/borghicremona"
commons_path="${movida_path}/commons"
hashmap_path="${bc_path}/hashmap"
graph_path="${bc_path}/graph"
sort_path="${bc_path}/sort"

function undo_build {
    read -p "You are deleting all the compiled files. Are you sure to continue [Y/n]? " -n 1 user_in
    case "${user_in}" in
        "Y" | "y" | "")
        echo ""
        rm ${hashmap_path}/*.class
        rm ${bc_path}/*.class
        rm ${commons_path}/*.class
		rm ${graph_path}/*.class
		rm ${sort_path}/*.class
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
    javac ${bc_path}/*.java
    javac ${hashmap_path}/*.java
	javac ${graph_path}/*.java
	javac ${sort_path}/*.java
    javac ${commons_path}/*.java
    ;;

    "-b=borghicremona")
    javac ${bc_path}/*.java
    javac ${hashmap_path}/*.java
	javac ${graph_path}/*.java
	javac ${sort_path}/*.java
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

	"-b=sort")
	javac ${sort_path}/*.java
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
