package com.mysite.core.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModel {

    // --- 1. Logo Fields ---
    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    private String logoLink;

    // --- 2. Button Fields ---
    @ValueMapValue
    private String searchLabel;

    @ValueMapValue
    private String searchLink;

    @ValueMapValue
    private String signInLabel;

    @ValueMapValue
    private String signInLink;

    // --- 3. Navigation Items (Your original code) ---
    @ChildResource(name = "navigationItems")
    private Resource navItemsRes;

    private List<NavItem> navigationItems;

    @PostConstruct
    protected void init() {
        navigationItems = new ArrayList<>();
        if (navItemsRes != null) {
            for (Resource itemRes : navItemsRes.getChildren()) {
                ValueMap vm = itemRes.getValueMap();
                navigationItems.add(
                        new NavItem(
                                vm.get("title", String.class),
                                vm.get("link", String.class)
                        )
                );
            }
        }
    }

    // --- Getters ---
    public String getLogoPath() { return logoPath; }
    public String getLogoLink() { return logoLink; }
    public String getSearchLabel() { return searchLabel; }
    public String getSearchLink() { return searchLink; }
    public String getSignInLabel() { return signInLabel; }
    public String getSignInLink() { return signInLink; }
    public List<NavItem> getNavigationItems() { return navigationItems; }

    // --- Inner Class ---
    public static class NavItem {
        private String title;
        private String link;

        public NavItem(String title, String link) {
            this.title = title;
            this.link = link;
        }

        public String getTitle() { return title; }
        public String getLink() { return link; }
    }
}