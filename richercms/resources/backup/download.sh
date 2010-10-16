# Tres bon PDF : http://bulkloadersample.appspot.com/showfile/bulkloader-presentation.pdf
# Il faut python 2.5 (< 3.0) et le jdk appengine python installe sur son poste.
# http://bulkloadersample.appspot.com/showfile/bulkloader-presentation.pdf

appcfg.py download_data --config_file=config.yml --filename=user.xml --kind=User --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=translationPage.xml --kind=TranslationPage --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=memoryFileItem.xml --kind=MemoryFileItem --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=tag.xml --kind=Tag --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=rootArbo.xml --kind=RootArbo --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=logInfo.xml --kind=LogInfo --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=language.xml --kind=Language --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=configuration.xml --kind=Configuration --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=template.xml --kind=Template --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms

appcfg.py download_data --config_file=config.yml --filename=arboPage.xml --kind=ArboPage --url=http://20.latest.richer-cms.appspot.com/remote_api --application=richer-cms
