#!/usr/bin/env bash
# Copy the deployment templates from the extracted lambda functions into templates directory
# Assumes you have ran aws configure and have output-mode as "text"
# Assumes you have wget installed and ready to use
# Works with "aws-cli/1.16.139 Python/2.7.10 Darwin/18.2.0 botocore/1.12.129"
grab_template () {
    local function=$1
        # copy the templates into the templates directory
        yes | cp $function/template.yml ../models/$function.yml
        yes | cp $function/template.yaml ../models/$function.yaml
        # copy the templates into the java projects resources directory
        yes | cp $function/template.yml ../../resources/templates/crawled/aws/$function.yml
        yes | cp $function/template.yaml ../../resources/templates/crawled/aws/$function.yaml
}

cd functions
for function in $(ls)
do
    grab_template "$function"
done