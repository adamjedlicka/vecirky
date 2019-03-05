package vecirky.events;

import vecirky.models.Promoter;

/**
 * Event which indicates that a promoter was deleted from the database.
 */
public class PromoterDeleted {

    private Promoter promoter;

    public PromoterDeleted(Promoter promoter) {
        this.promoter = promoter;
    }

    public Promoter getPromoter() {
        return promoter;
    }
}
