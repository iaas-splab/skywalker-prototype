#!/usr/bin/env bash
# Execute the grabbing scripts
# Assumes you have ran aws configure and have output-mode as "text"
# Assumes you have wget installed and ready to use
# Works with "aws-cli/1.16.139 Python/2.7.10 Darwin/18.2.0 botocore/1.12.129"
sh grab_lambdas.sh
sh grab_models.sh
printf "Successfully extracted all lambda functions and deployment templates."