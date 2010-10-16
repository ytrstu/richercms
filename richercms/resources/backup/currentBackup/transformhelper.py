#!/usr/bin/python2.5
# Copyright 2009 Google Inc.
#

"""Additional transform helpers.

These have been integrated into google.appengine.ext.bulkload.transform.
"""

from google.appengine.ext.bulkload import transform


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

def list_from_child_node(xpath, suppress_blank=False):
  return transform.list_from_child_node(xpath, suppress_blank)


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



# Hierarchical key helpers.

def extract_deep_key(index):
  return transform.key_id_or_name_as_string_n(index)


def extract_deep_key_kind(index):
  return transform.key_kind_n(index)


def extract_deep_key_type(index):
  return transform.key_type_n(index)