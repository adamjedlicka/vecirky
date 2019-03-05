package vecirky.gui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import vecirky.C;
import vecirky.G;
import vecirky.database.factories.EventFactory;
import vecirky.events.ClientCreated;
import vecirky.events.EventCreated;
import vecirky.events.PromoterCreated;
import vecirky.repositories.ClientRepository;
import vecirky.repositories.PromoterRepository;
import vecirky.repositories.EventRepository;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Calendar;
import java.util.Date;

import vecirky.events.ClientDeleted;
import vecirky.events.ClientUpdated;
import vecirky.events.EventDeleted;
import vecirky.events.EventUpdated;
import vecirky.events.PromoterDeleted;
import vecirky.events.PromoterUpdated;
import vecirky.models.Event;

public class App {

    private ClientRepository clientRepository = C.get(ClientRepository.class);

    private PromoterRepository promoterRepository = C.get(PromoterRepository.class);

    private EventRepository eventRepository = C.get(EventRepository.class);

    @FXML
    private Tab homeTab;

    @FXML
    private Tab eventsTab;

    @FXML
    private Tab clientsTab;

    @FXML
    private Tab promotersTab;

    @FXML
    private Label totalClientsLabel;

    @FXML
    private Label totalPromotersLabel;

    @FXML
    private Label totalEventsLabel;

    @FXML
    private Label totalPricesLabel;

    @FXML
    private Label eventsWeeklyLabel;

    @FXML
    private VBox notificationArea;

    public void initialize() throws IOException {
        Parent events = FXMLLoader.load(G.getResource("vecirky/gui/agendas/EventAgenda.fxml"));
        eventsTab.setContent(events);

        Parent clients = FXMLLoader.load(G.getResource("vecirky/gui/agendas/ClientAgenda.fxml"));
        clientsTab.setContent(clients);

        Parent promoters = FXMLLoader.load(G.getResource("vecirky/gui/agendas/PromoterAgenda.fxml"));
        promotersTab.setContent(promoters);

        C.eventBus().subscribe(ClientCreated.class, e -> {
            onClientCreationAttepmt();
        });

        C.eventBus().subscribe(EventCreated.class, e -> {
            onEventCreationAttempt();
        });

        C.eventBus().subscribe(PromoterCreated.class, e -> {
            onPromoterCreationAttempt();
        });

        C.eventBus().subscribeAndRunWith(ClientCreated.class, e -> clientSize(), null);
        C.eventBus().subscribeAndRunWith(ClientDeleted.class, e -> clientSize(), null);
        C.eventBus().subscribeAndRunWith(ClientUpdated.class, e -> clientSize(), null);

        C.eventBus().subscribeAndRunWith(PromoterCreated.class, e -> promoterSize(), null);
        C.eventBus().subscribeAndRunWith(PromoterDeleted.class, e -> promoterSize(), null);
        C.eventBus().subscribeAndRunWith(PromoterUpdated.class, e -> promoterSize(), null);

        C.eventBus().subscribeAndRunWith(EventCreated.class, e -> eventSize(), null);
        C.eventBus().subscribeAndRunWith(EventDeleted.class, e -> eventSize(), null);
        C.eventBus().subscribeAndRunWith(EventUpdated.class, e -> eventSize(), null);

        C.eventBus().subscribeAndRunWith(EventCreated.class, e -> eventPrices(), null);
        C.eventBus().subscribeAndRunWith(EventDeleted.class, e -> eventPrices(), null);
        C.eventBus().subscribeAndRunWith(EventUpdated.class, e -> eventPrices(), null);

        C.eventBus().subscribeAndRunWith(EventCreated.class, e -> currentEvents(), null);
        C.eventBus().subscribeAndRunWith(EventDeleted.class, e -> currentEvents(), null);
        C.eventBus().subscribeAndRunWith(EventUpdated.class, e -> currentEvents(), null);

        notificationArea.setAlignment(Pos.BOTTOM_CENTER);
        notificationArea.setMouseTransparent(true);
    }

    private void clientSize() {
        totalClientsLabel.setText("" + clientRepository.findAll().size());
    }

    private void promoterSize() {
        totalPromotersLabel.setText("" + promoterRepository.findAll().size());
    }

    private void eventSize() {
        totalEventsLabel.setText("" + eventRepository.findAll().size());
    }

    private void eventPrices() {
        int sum = 0;
        for (Event event : eventRepository.findAll()) {
            sum += event.getPrice();
        }
        totalPricesLabel.setText("" + sum);
    }

    private void currentEvents() {
        int evnum = 0;
        Date todayDate = new Date();
        Date dateWeekAfter = new Date();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.WEEK_OF_YEAR, 1);
        dateWeekAfter = now.getTime();
        for (Event event : eventRepository.findAll()) {
            if (event.getEventDate().after(todayDate) && event.getEventDate().before(dateWeekAfter)) {
                evnum = ++evnum;
            }
        }
        eventsWeeklyLabel.setText("" + evnum);
    }

    @FXML
    private void onHomeTabAction() {
        if (!homeTab.isSelected()) return;


    }

    @FXML
    private void onClientsTabAction() {
        if (!clientsTab.isSelected()) return;
    }

    @FXML
    private void onFakeDataAction() {
        for (int i = 0; i < 50; i++) {
            EventFactory.create();
        }
    }

    private void onClientCreationAttepmt() {
        AnchorPane notification;
        try {
            notification = FXMLLoader.load(G.getResource("vecirky/gui/popup-success.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Label label = (Label) notification.getChildren().get(0);
        label.setText("Klient byl uložen.");
        notificationArea.getChildren().add(notification);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                FadeTransition ft = new FadeTransition(Duration.millis(4000), notification);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setOnFinished(event -> {
                    notificationArea.getChildren().remove(0);
                });
                ft.play();
            }
        }, 5000);
    }

    private void onEventCreationAttempt(){
        AnchorPane notification;
        try{
            notification = FXMLLoader.load(G.getResource("vecirky/gui/popup-success.fxml"));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        Label label = (Label) notification.getChildren().get(0);
        label.setText("Event byl vytvořen.");
        notificationArea.getChildren().add(notification);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                FadeTransition ft = new FadeTransition(Duration.millis(4000), notification);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setOnFinished(event -> {
                    notificationArea.getChildren().remove(0);
                });
                ft.play();
            }
        }, 5000);
    }

    private void onPromoterCreationAttempt(){
        AnchorPane notification;
        try{
            notification = FXMLLoader.load(G.getResource("vecirky/gui/popup-success.fxml"));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        Label label = (Label) notification.getChildren().get(0);
        label.setText("Pořadatel vytvořen.");
        notificationArea.getChildren().add(notification);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                FadeTransition ft = new FadeTransition(Duration.millis(4000), notification);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setOnFinished(event -> {
                    notificationArea.getChildren().remove(0);
                });
                ft.play();
            }
        }, 5000);
    }
}
