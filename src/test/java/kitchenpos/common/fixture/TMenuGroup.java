package kitchenpos.common.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kitchenpos.domain.MenuGroup;

public class TMenuGroup {

    public static MultiBuilder multiBuilder() {
        return new MultiBuilder();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class MultiBuilder {

        private List<MenuGroup> menuGroups;

        public MultiBuilder() {
            this.menuGroups = new ArrayList<>();
        }

        public MultiBuilder create(String name) {
            this.menuGroups.add(generate(name));
            return this;
        }

        public MultiBuilder create(String name, int repeat) {
            for (int i = 0; i < repeat; i++) {
                this.menuGroups.add(generate(name));
            }
            return this;
        }

        public MultiBuilder createRandom() {
            this.menuGroups.add(generate(UUID.randomUUID().toString()));
            return this;
        }

        public MultiBuilder createRandom(int repeat) {
            for (int i = 0; i < repeat; i++) {
                this.menuGroups.add(generate(UUID.randomUUID().toString()));
            }
            return this;
        }

        private MenuGroup generate(String name) {
            MenuGroup menuGroup = new MenuGroup();
            menuGroup.setName(name);
            return menuGroup;
        }

        public List<MenuGroup> build() {
            return menuGroups;
        }
    }

    public static class Builder {

        private String name;

        public Builder create(String name) {
            this.name = name;
            return this;
        }

        public Builder createRandom() {
            this.name = UUID.randomUUID().toString();
            return this;
        }

        public MenuGroup build() {
            MenuGroup menuGroup = new MenuGroup();
            menuGroup.setName(name);
            return menuGroup;
        }
    }
}
