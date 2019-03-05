package vecirky.events;

import vecirky.models.Promoter;

/**
 * Event which indicates that already existing event was updated in the database.
 */
public class PromoterUpdated {

    private Promoter promoter;

    public PromoterUpdated(Promoter promoter) {
        this.promoter = promoter;
    }

    public Promoter getPromoter() {
        return promoter;
    }

}

