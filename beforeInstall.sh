#!/bin/bash
# Backup existing appspec.yml if it exists
if [ -f /home/ec2-user/appspec.yml ]; then
  mv /home/ec2-user/appspec.yml /home/ec2-user/appspec.yml.bak
fi