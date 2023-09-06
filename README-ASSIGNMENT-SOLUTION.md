# Assignment Solution Steps

## Known Issues

* CSS and lok and feel of components do not match
* Hero Banner Carousel first slide render after 10 sec, once it loads, the transition effect is smooth
* In Recent News Component, sorting logic for article publication date is not working as per the asking
* Search component CSS also does not match as per assignment problem statement


## Features of Hero Banner Component

* Bootstrap5 Carousel is implemented
* new bootstrap clientlib created and rendered through page and xf component
* New Sling Model created
* Reusable service created to fetch JCR resources using Query Builder API
* reusable carousel slide component created which then renders in a loop from custom carousel component
* Custom Carousel component fetches all the individual carousel items from an Experience Fragment designated to author carousel slide components individually 
* Admin session to fire JCR queries is obtained using Service Resource resolver design pattern

## Features of Recent News Component

* Complex Sorting logic implemented in order to sort the news-card component Sling Resource as per the "featured news with image property" and "news publication date property" but due to time constraint, the logic doesn't work as expected and need further debugging in order to resolve minor issues
* Bootstrap5 Card component is implemented
* New Sling Model created but it invokes a reusable method from the existing service to query individual news card components from the designated experience fragment and aggregates them to render recent news component
* Reusable service created to fetch JCR resources using Query Builder API
* Recent News component fetches all the individual news card component from an Experience Fragment designated to author all news cards for autoring friendly experience

## Features of Quick Search Component

* Search component reuses Core Search component
* Proxy Component Pattern and Sling Delegation Pattern is applied here to attain reusability

## Conclusion

* Please refer to screenshots in the response email of authored components and their previwed renditions
* Please install wknd site 3.2.0 version on your CRX instance, I used DAM assets from the wknd site content for authoring mysite content pages
