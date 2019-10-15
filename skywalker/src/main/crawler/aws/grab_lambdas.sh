#!/usr/bin/env bash
# Parallelly download all aws-lambda functions
# Assumes you have ran aws configure and have output-mode as "text"
# Assumes you have wget installed and ready to use
# Works with "aws-cli/1.16.139 Python/2.7.10 Darwin/18.2.0 botocore/1.12.129"
download_code () {
    local run=$1
        aws lambda get-function --function-name $run --profile ayhan | grep "Location" | awk -F ": " '{ print $2}' | sed 's/"//g' > $run.txt
        wget -i $run.txt -O $run.zip
        yes | unzip $run.zip -d functions/$run/
        yes | mkdir ../../../../resources/packages/thumbnailer/extracted/functions/$run/resources
        yes | mv $run.txt ../../../../resources/packages/thumbnailer/extracted/$run.txt
        yes | mv $run.zip ../../../../resources/packages/thumbnailer/extracted/$run.zip
}

excluded_function="CreateThumbnail"
for run in $(aws lambda list-functions | cut -f 6 | xargs)
do
    download_code "$run"
done