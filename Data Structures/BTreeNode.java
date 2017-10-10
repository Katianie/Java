package Trees;

/** Trees - BTreeNode.java
* Defines a binary tree node class capable of 
* storing an object reference and a reference to the left and right
* subtrees.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public class BTreeNode<T>
{
  private T data;
  private BTreeNode<T> left, right;


  public BTreeNode()
  {
    data = null;
    left = right = null;
  }

  public BTreeNode(T newItem)
  {
    data = newItem;
    left = right = null;
  }

  public T getItem()
  {
    return data;
  }

  public BTreeNode<T> getLeft()
  {
    return left;
  }

  public BTreeNode<T> getRight()
  {
    return right;
  }

  public void setItem(T newItem)
  {
    data = newItem;
  }

  public void setLeft(BTreeNode<T> child)
  {
    left = child;
  }

  public void setRight(BTreeNode<T> child)
  {
    right = child;
  }

}
