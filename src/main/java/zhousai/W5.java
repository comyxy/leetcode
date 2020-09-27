package zhousai;

import java.util.*;

/** 2020/9/26 */
public class W5 {
  public double entropy(String content) {
    int[] m = new int[4];
    for (int i = 0; i < content.length(); i++) {
      m[content.charAt(i) - 'A']++;
    }
    double h = 0.0;
    for (int count : m) {
      if (count == 0) {
        continue;
      }
      double tmp = ((double) count) / content.length();
      double lo = Math.log(tmp) / Math.log(2);
      h += tmp * lo;
    }
    h = (double) Math.round(h * 100) / 100;
    return -h;
  }

  public static void q1() {
    Scanner scanner = new Scanner(System.in);
    Map<String, Node> map = new HashMap<>();
    List<Node> list = new ArrayList<>();
    boolean isP = false, isO = false;
    while (scanner.hasNextLine()) {
      String s = scanner.nextLine();
      if (s.equals("performance")) {
        isO = false;
        isP = true;
      } else if (s.equals("organization")) {
        isO = true;
        isP = false;
      } else {
        if (isP) {
          String[] split = s.split(",");
          Node yNode = new Node(split[0], Integer.parseInt(split[1]), 2);
          map.put(split[0], yNode);
        } else if (isO) {
          String[] split = s.split(",");
          Node mNode;
          if (!map.containsKey(split[0])) {
            mNode = new Node(split[0], 0, 0);
            map.put(split[0], mNode);
            list.add(mNode);
          } else {
            mNode = map.get(split[0]);
          }
          Node tNode;
          if (!map.containsKey(split[1])) {
            tNode = new Node(split[1], 0, 1);
            map.put(split[1], tNode);
          } else {
            tNode = map.get(split[1]);
          }
          Node yNode = map.get(split[2]);
          tNode.children.add(yNode);
          tNode.val += yNode.val;
          mNode.children.add(tNode);
          mNode.val += yNode.val;
        }
      }
    }
    list.sort((o1, o2) -> o2.val - o1.val);
    for (Node node : list) {
      System.out.println(node.name + "<" + node.val + ">");
      for (Node tNode : node.children) {
        System.out.println("-" + tNode.name + "<" + tNode.val + ">");
        for (Node yNode : tNode.children) {
          System.out.println("--" + yNode.name + "<" + yNode.val + ">");
        }
      }
    }
  }

  static class Node {
    private String name;
    private int val;
    private int type;
    private Set<Node> children;

    public Node() {}

    public Node(String name, int val, int type) {
      this.name = name;
      this.val = val;
      this.type = type;
      if (type != 2)
        this.children =
            new TreeSet<>(
                new Comparator<Node>() {
                  @Override
                  public int compare(Node o1, Node o2) {
                    return o2.val - o1.val;
                  }
                });
    }

    @Override
    public String toString() {
      return "Node{" + "name='" + name + '\'' + ", val=" + val + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
      char[] chars = name.toCharArray();
      int hash = 0;
      for (char c : chars) {
        hash = hash * 131 + c;
      }
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      return name.equals(((Node) obj).name);
    }
  }

  public int minOperations(String[] logs) {
    int depth = 0;
    for (String log : logs) {
      if ("./".equals(log)) {

      } else if ("../".equals(log)) {
        if (depth > 0) {
          depth--;
        }
      } else {
        depth++;
      }
    }
    return depth;
  }

  public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
    int profit = 0, count = 0, max = 0;
    int ans = -1;
    for (int i = 0; i < customers.length; i++) {
      count += customers[i];
      int newProfit;
      if (count >= 4) {
        newProfit = 4 * boardingCost - runningCost + profit;
        count -= 4;
      } else {
        newProfit = count * boardingCost - runningCost + profit;
        count = -0;
      }
      if (newProfit > max) {
        max = newProfit;
        ans = i + 1;
      }
      profit = newProfit;
    }
    while (count > 0) {
      int newProfit;
      if (count >= 4) {
        newProfit = 4 * boardingCost - runningCost + profit;
        count -= 4;
      } else {
        newProfit = count * boardingCost - runningCost + profit;
        count = -0;
      }
      if (newProfit > max) {
        max = newProfit;
        ans++;
      }
      profit = newProfit;
    }
    return ans;
  }

  static class ThroneInheritance {
    private Person king;
    private Map<String, Person> map;

    public ThroneInheritance(String kingName) {
      king = new Person(kingName);
      map = new HashMap<>();
      map.put(kingName, king);
    }

    public void birth(String parentName, String childName) {
      Person parent = map.get(parentName);
      Person child = new Person(childName);
      parent.children.add(child);
      map.put(childName, child);
    }

    public void death(String name) {
      map.get(name).isDead = true;
    }

    public List<String> getInheritanceOrder() {
      List<String> ans = new ArrayList<>();
      Deque<Person> st = new LinkedList<>();
      st.push(king);
      while (!st.isEmpty()) {
        Person p = st.pop();
        if (!p.isDead) {
          ans.add(p.name);
        }
        for (Person child : p.children) {
          st.push(child);
        }
      }
      return ans;
    }

    static class Person {
      private static int index = 0;

      private int id;
      private String name;
      private Set<Person> children;
      private boolean isDead = false;

      public Person(String name) {
        this.id = index++;
        this.name = name;
        this.children = new TreeSet<>((o1, o2) -> o2.id - o1.id);
      }

      @Override
      public int hashCode() {
        char[] chars = name.toCharArray();
        int hash = 0;
        for (char c : chars) {
          hash = hash * 131 + c;
        }
        return hash;
      }

      @Override
      public boolean equals(Object obj) {
        return name.equals(((Person) obj).name);
      }
    }
  }

  private int max = 0;
  public int maximumRequests(int n, int[][] requests) {
    int count = 0;
    int[] degree = new int[n];
    helper(requests,degree,count,0);
    return max;
  }

  private void helper(int[][] requests, int[] degree, int count, int index) {
    if(check(degree)){
      max = Math.max(max, count);
    }
    for (int i = index; i < requests.length; i++) {
      degree[requests[i][0]]--;
      degree[requests[i][1]]++;
      count++;
      helper(requests, degree, count, i + 1);
      degree[requests[i][0]]++;
      degree[requests[i][1]]--;
      count--;
    }
  }

  private boolean check(int[] degree) {
    for (int i = 0; i < degree.length; i++) {
      if(degree[i] != 0){
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {

    W5 w5 = new W5();
    //    String[] logs = {"d1/","d2/","../","d21/","./"};
    //    int minOperations = w5.minOperations(logs);
    //    System.out.println(minOperations);

    //    int [] customers = {8,3};
    //    int minOperationsMaxProfit = w5.minOperationsMaxProfit(customers, 5, 6);
    //    System.out.println(minOperationsMaxProfit);

    //    ThroneInheritance t = new ThroneInheritance("king");
    //    t.birth("king", "andy"); // 继承顺序：king > andy
    //    t.birth("king", "bob"); // 继承顺序：king > andy > bob
    //    t.birth("king", "catherine"); // 继承顺序：king > andy > bob > catherine
    //    t.birth("andy", "matthew"); // 继承顺序：king > andy > matthew > bob > catherine
    //    t.birth("bob", "alex"); // 继承顺序：king > andy > matthew > bob > alex > catherine
    //    t.birth("bob", "asha"); // 继承顺序：king > andy > matthew > bob > alex > asha > catherine
    //    List<String> inheritanceOrder =
    //        t
    //            .getInheritanceOrder(); // 返回 ["king", "andy", "matthew", "bob", "alex", "asha",
    //                                    // "catherine"]
    //    System.out.println(inheritanceOrder);
    //    t.death("bob"); // 继承顺序：king > andy > matthew > bob（已经去世）> alex > asha > catherine
    //    inheritanceOrder =
    //        t.getInheritanceOrder(); // 返回 ["king", "andy", "matthew", "alex", "asha", "catherine"]
    //    System.out.println(inheritanceOrder);


  }
}
