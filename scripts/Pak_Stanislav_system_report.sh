#!/bin/bash
LOG_FILE="$HOME/devops-rk/pak-stanislav-student-api/logs/Pak_Stanislav_system_report.log"
{
echo "Student: Pak Stanislav"
echo "Date: $(date)"
echo "User: $(whoami)"
echo "System name: "
uname
echo "Operating system information"
cat /etc/os-release
echo "Version Linux: $(uname -r)"
echo "Memory information: "
free -h
echo "Disk information: "
df -h
echo "CPU information: "
lscpu
} | tee -a "$LOG_FILE"
