package kitchenpos.application;

import static kitchenpos.support.fixtures.DomainFixtures.MENU_GROUP_NAME1;
import static kitchenpos.support.fixtures.DomainFixtures.MENU_GROUP_NAME2;
import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.dao.MenuGroupDao;
import kitchenpos.domain.MenuGroup;
import kitchenpos.support.cleaner.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @Autowired
    private MenuGroupDao menuGroupDao;

    @Test
    @DisplayName("MenuGroup을 생성한다.")
    void create() {
        MenuGroup menuGroup = menuGroupService.create(new MenuGroup(MENU_GROUP_NAME1));

        assertThat(menuGroupDao.existsById(menuGroup.getId())).isTrue();
    }

    @Test
    @DisplayName("MenuGroup을 모두 조회한다.")
    void list() {
        MenuGroup menuGroup1 = menuGroupService.create(new MenuGroup(MENU_GROUP_NAME1));
        MenuGroup menuGroup2 = menuGroupService.create(new MenuGroup(MENU_GROUP_NAME2));

        assertThat(menuGroupService.list()).containsExactly(menuGroup1, menuGroup2);
    }
}
