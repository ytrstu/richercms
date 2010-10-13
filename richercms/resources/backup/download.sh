# Très bon PDF : http://bulkloadersample.appspot.com/showfile/bulkloader-presentation.pdf
# Il faut python 2.5 (< 3.0) et le jdk appengine python installé sur son poste.

appcfg.py download_data --config_file=config.yml --filename=user.xml --kind=User --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms
appcfg.py download_data --config_file=config.yml --filename=translationPage.xml --kind=TranslationPage --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms
# Le fichier config.yml n'est pas encore correcte pour ArboPage
appcfg.py download_data --config_file=config.yml --filename=arboPage.xml --kind=ArboPage --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms
