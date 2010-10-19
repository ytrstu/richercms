#!/usr/bin/python2.5
# Copyright 2009 Google Inc.
#

"""Additional transform helpers.

These have been integrated into google.appengine.ext.bulkload.transform.
"""

import os
import re
import tempfile

from google.appengine.api import datastore
from google.appengine.ext.bulkload import transform
from google.appengine.api import datastore_types


# List property helpers.

def split_string(delimeter):
  return transform.split_string(delimeter)

def join_list(delimeter):
  return transform.join_list(delimeter)


def list_from_multiproperty(external_names):
  return transform.list_from_multiproperty(*external_names)


def property_from_list(index):
  return transform.property_from_list(index)


# SimpleXML list Helpers

def list_from_child_node(xpath):
  """Return a list property from child nodes of the current xml node.

  This applies only the simplexml helper, as it assumes __node__, the current
  ElementTree node corresponding to the import record.

  Sample usage for structure:
   <Visit>
    <VisitActivities>
     <Activity>A1</Activity>
     <Activity>A2</Activity>
    </VisitActivities>
   </Visit>

  property: activities
  external_name: VisitActivities # Ignored on import, used on export.
  import_transform: list_from_xml_node('VisitActivities/Activity')
  export_transform: child_node_from_list('Activity')

  Args:
    xpath: XPath to run on the current node.
    suppress_blank: if True, ndoes with no text will be skipped.

  Returns:
    Transform function which works as described in the args.
  """

  def list_from_child_node_lambda(unused_value, bulkload_state):
    result = []
    for node in bulkload_state.current_dictionary['__node__'].findall(xpath):
      if node.text:
        result.append(long(node.text))
    if len(result)==0:
      return None
    return result

  return list_from_child_node_lambda

def key_list_from_child_node(xpath, kind):
  """Return a list property from child nodes of the current xml node.

  This applies only the simplexml helper, as it assumes __node__, the current
  ElementTree node corresponding to the import record.

  Sample usage for structure:
   <Visit>
    <VisitActivities>
     <Activity>A1</Activity>
     <Activity>A2</Activity>
    </VisitActivities>
   </Visit>

  property: activities
  external_name: VisitActivities # Ignored on import, used on export.
  import_transform: list_from_xml_node('VisitActivities/Activity')
  export_transform: child_node_from_list('Activity')

  Args:
    xpath: XPath to run on the current node.
    suppress_blank: if True, ndoes with no text will be skipped.

  Returns:
    Transform function which works as described in the args.
  """

  def key_list_from_child_node_lambda(unused_value, bulkload_state):
    result = []
    for node in bulkload_state.current_dictionary['__node__'].findall(xpath):
      if node.text:
        result.append(datastore.Key.from_path(kind, long(node.text)))
    return result

  return key_list_from_child_node_lambda


def child_node_from_list(child_node_name):
  """Return a value suitable for generating an XML child node on export.

  The return value is a list of tuples which the simplexml connector will
  use to build a child node.

  See also list_from_child_node

  Args:
    child_node_name: The name to use for each child node.

  Returns:
    Transform function which works as described in the args.
  """

  def child_node_from_list_lambda(values):
    if values == '' or values is None:
      return []
    return [(child_node_name, str(value)) for value in values]

  return child_node_from_list_lambda

def child_node_from_list_key(child_node_name):
  """Return a value suitable for generating an XML child node on export.

  The return value is a list of tuples which the simplexml connector will
  use to build a child node.

  See also list_from_child_node

  Args:
    child_node_name: The name to use for each child node.

  Returns:
    Transform function which works as described in the args.
  """

  def child_node_from_list_key_lambda(values):
    return [(child_node_name, transform.key_id_or_name_as_string(value)) for value in values]

  return child_node_from_list_key_lambda


def blob_to_file(filename_hint_propertyname=None,
                 directory_hint=''):
  """Write the blob contents to a file, and replace them with the filename.

  Args:
    filename_hint_propertyname: If present, the filename will begin with
      the contents of this value in the entity being exported.
    directory_hint: If present, the files will be stored in this directory.

  Returns:
    A function which writes the input blob to a file.
  """
  directory = []

  def transform_function(value, bulkload_state):
    if not directory:
      parent_dir = os.path.dirname(bulkload_state.filename)
      directory.append(os.path.join(parent_dir, directory_hint))
      if directory[0] and not os.path.exists(directory[0]):
        os.makedirs(directory[0])

    filename_hint = 'blob_'
    suffix = ''
    filename = ''
    if filename_hint_propertyname:
      filename_hint = bulkload_state.current_dictionary.get("key") + "_" + bulkload_state.current_entity[filename_hint_propertyname]
      filename = os.path.join(directory[0], filename_hint)
      if os.path.exists(filename):
        filename = ''
        (filename_hint, suffix) = os.path.splitext(filename_hint)
    if not filename:
      filename = tempfile.mktemp(suffix, filename_hint, directory[0])
    f = open(filename, 'wb')
    f.write(value)
    f.close()
    return filename

  return transform_function

def file_to_blob():
  """Write the blob contents to a file, and replace them with the filename.

  Args:
    filename_hint_propertyname: If present, the filename will begin with
      the contents of this value in the entity being exported.
    directory_hint: If present, the files will be stored in this directory.

  Returns:
    A function which writes the input blob to a file.
  """

  def transform_function(value, bulkload_state):
    directory = os.path.dirname(bulkload_state.filename)

    suffix = ''
    filename = ''
    filename_hint = value
    filename = os.path.join(directory, filename_hint)
    f = open(filename, 'r')
    data = f.read()
    f.close()
    return datastore_types.Blob(data)

  return transform_function


# Hierarchical key helpers.

def extract_deep_key(index):
  return transform.key_id_or_name_as_string_n(index)


def extract_deep_key_kind(index):
  return transform.key_kind_n(index)


def extract_deep_key_type(index):
  return transform.key_type_n(index)
