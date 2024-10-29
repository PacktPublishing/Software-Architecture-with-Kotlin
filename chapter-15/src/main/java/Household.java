import java.util.ArrayList;
import java.util.List;

public class Household {
    private final String name;
    private final List<String> members = new ArrayList<>();

    public Household(String name, List<String> members) {
        this.name = name;
        this.members.addAll(members);
    }

    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        return new ArrayList(members);
    }
}

