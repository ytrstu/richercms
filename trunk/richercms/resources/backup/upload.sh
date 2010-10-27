#!/bin/sh

url=http://fer.appspot.com/remote_api
appli=fer
bandwidth_limit=50000
email=VotreEmail
password=VotreMotDePasse

appcfg.py upload_data --config_file=config.yml --filename=user.xml --kind=User --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=translationPage.xml --kind=TranslationPage --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=memoryFileItem.xml --kind=MemoryFileItem --num_threads=1 --batch_size=1 --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=tag.xml --kind=Tag --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=rootArbo.xml --kind=RootArbo --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=logInfo.xml --kind=LogInfo --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=language.xml --kind=Language --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=configuration.xml --kind=Configuration --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=template.xml --kind=Template --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py upload_data --config_file=config.yml --filename=arboPage.xml --kind=ArboPage --bandwidth_limit=$bandwidth_limit --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

