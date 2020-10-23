package core;

import java.util.ArrayList;
import java.util.List;

public class Project extends Container{

  private List<Container> containerChildren = new ArrayList<Container>();

//constructor
  public Project(){}
  public Project(String name, String desc, Container containerFather) {
    // Auto-generated constructor
    super(name, desc, containerFather);
  }

  public void addChild(Container child){
    containerChildren.add(child);
    child.containerFather = this;
  }

  public List<Container> getContainerChildren() {
    return containerChildren;
  }

  public void setContainerChildren(List<Container> containerChildren) {
    this.containerChildren = containerChildren;
  }

}
