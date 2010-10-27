#!/bin/sh
# Tres bon PDF : http://bulkloadersample.appspot.com/showfile/bulkloader-presentation.pdf
# Il faut python 2.5 (< 3.0) et le jdk appengine python installe sur son poste.

url=http://20.latest.richer-cms.appspot.com/remote_api
appli=richer-cms
email=VotreEmail
password=VotreMotDePasse

rm -rf *.xml images

appcfg.py download_data --config_file=config.yml --filename=user.xml --kind=User --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=translationPage.xml --kind=TranslationPage --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=memoryFileItem.xml --kind=MemoryFileItem --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=tag.xml --kind=Tag --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=rootArbo.xml --kind=RootArbo --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=logInfo.xml --kind=LogInfo --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=language.xml --kind=Language --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=configuration.xml --kind=Configuration --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=template.xml --kind=Template --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

appcfg.py download_data --config_file=config.yml --filename=arboPage.xml --kind=ArboPage --url=$url --application=$appli --email=$email --passin <<-EOF
$password
EOF

