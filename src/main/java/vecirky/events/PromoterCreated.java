package vecirky.events;

import vecirky.models.Promoter;

/**
 * Event that indicates that a new promoter was created and stored into the database.
 */
public class PromoterCreated {

    private Promoter promoter;

    public PromoterCreated(Promoter promoter) {
        this.promoter = promoter;
    }

    public Promoter getPromoter() {
        return promoter;
    }
}
