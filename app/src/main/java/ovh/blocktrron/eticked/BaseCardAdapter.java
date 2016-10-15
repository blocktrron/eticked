package ovh.blocktrron.eticked;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ovh.blocktrron.eticked.dataset.Cards.ApplicationActivationCard;
import ovh.blocktrron.eticked.dataset.Cards.ApplicationDetailsCard;
import ovh.blocktrron.eticked.dataset.Cards.BaseCard;
import ovh.blocktrron.eticked.dataset.Cards.JourneyCard;
import ovh.blocktrron.eticked.dataset.Cards.MessageCard;
import ovh.blocktrron.eticked.dataset.Cards.TicketActivationCard;
import ovh.blocktrron.eticked.dataset.eticket.Permit;
import ovh.blocktrron.eticked.dataset.eticket.Product;

public class BaseCardAdapter extends RecyclerView.Adapter<BaseCardAdapter.ViewHolder> {
    private ArrayList<BaseCard> dataset;

    public BaseCardAdapter() {
        dataset = new ArrayList<>();
    }

    public BaseCardAdapter(ArrayList<BaseCard> al) {
        dataset = al;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case BaseCard.TAG_JOURNEY:
                itemView = LayoutInflater.
                        from(parent.getContext())
                        .inflate(R.layout.card_journey, parent, false);
                return new BaseCardAdapter.JourneyViewHolder(itemView);
            case BaseCard.TAG_ACTIVATION_CARD:
                itemView = LayoutInflater.
                        from(parent.getContext())
                        .inflate(R.layout.card_appactivation, parent, false);
                return new ApplicationActivationViewHolder(itemView);
            case BaseCard.TAG_ACTIVATION_TICKET:
                itemView = LayoutInflater.
                        from(parent.getContext())
                        .inflate(R.layout.card_ticketactivation, parent, false);
                return new TicketActivationViewHolder(itemView);
            case BaseCard.TAG_MESSAGE:
                itemView = LayoutInflater.
                        from(parent.getContext())
                        .inflate(R.layout.card_message, parent, false);
                return new MessageViewHolder(itemView);
            case BaseCard.TAG_APPDETAIL:
                itemView = LayoutInflater.
                        from(parent.getContext())
                        .inflate(R.layout.card_appdetails, parent, false);
                return new ApplicationDetailViewHolder(itemView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).describeContents();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.loadData(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(ArrayList<BaseCard> in) {
        dataset = in;
        notifyDataSetChanged();
    }

    public ArrayList<BaseCard> getDataset() {
        return dataset;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void loadData(BaseCard applicationLogEntry);
    }

    public class JourneyViewHolder extends ViewHolder {
        private TextView asn;
        private TextView psn;
        private TextView date;
        private TextView time;
        private TextView station;
        private TextView terminal;
        private TextView operator;
        private TextView station_name;
        private TextView permit_id;
        private TextView product_id;
        private TextView route_id;
        private TextView sam_id;
        private TextView sam_sn;

        public JourneyViewHolder(View itemView) {
            super(itemView);
            asn = (TextView) itemView.findViewById(R.id.check_asn);
            psn = (TextView) itemView.findViewById(R.id.check_psn);
            date = (TextView) itemView.findViewById(R.id.check_date);
            time = (TextView) itemView.findViewById(R.id.check_time);
            station = (TextView) itemView.findViewById(R.id.check_stationID);
            terminal = (TextView) itemView.findViewById(R.id.check_tid);
            operator = (TextView) itemView.findViewById(R.id.check_operator);
            station_name = (TextView) itemView.findViewById(R.id.check_station_name);
            permit_id = (TextView) itemView.findViewById(R.id.check_permit_id);
            product_id = (TextView) itemView.findViewById(R.id.check_product_id);
            route_id = (TextView) itemView.findViewById(R.id.check_route_id);
            sam_id = (TextView) itemView.findViewById(R.id.check_sam_id);
            sam_sn = (TextView) itemView.findViewById(R.id.check_sam_sn);
        }

        public void loadData(BaseCard applicationLogEntry) {
            JourneyCard journeyCard = (JourneyCard) applicationLogEntry;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);
            asn.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.transactionData.getApplicationSequenceNumber()));
            psn.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.journeyTransactionData.getPermitSequenceNumber()));
            date.setText(sdf.format(journeyCard.transactionData.getDateTime().getCalendar().getTime()));
            time.setText(stf.format(journeyCard.transactionData.getDateTime().getCalendar().getTime()));
            station.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.transactionData.location.getID()));
            terminal.setText(
                    String.format(Locale.GERMANY, "%d-%d",
                            journeyCard.transactionData.getTerminalID().getID(),
                            journeyCard.transactionData.getTerminalID().getOrganisationID()));
            operator.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.transactionData.location.getOrganisationID()));
            Permit permit = journeyCard.journeyTransactionData.getPermitID();
            permit_id.setText(
                    String.format(Locale.GERMANY, "%d-%d",
                            permit.getID(),
                            permit.getOrganisationID()));
            Product product = journeyCard.journeyTransactionData.getProductID();
            product_id.setText(
                    String.format(Locale.GERMANY, "%d-%d",
                            product.getID(),
                            product.getOrganisationID()));
            if (journeyCard.journeyTransactionData.getRouteVariant().getRouteName() != null) {
                route_id.setText(
                        String.format(Locale.GERMANY, "%d (%s)",
                                journeyCard.journeyTransactionData.getRouteVariant().getRoute(),
                                journeyCard.journeyTransactionData.getRouteVariant().getRouteName()));
            } else {
                route_id.setText(
                        String.format(Locale.GERMANY, "%d",
                                journeyCard.journeyTransactionData.getRouteVariant().getRoute()));
            }
            sam_id.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.transactionData.getTransactionID().getSamID()));
            sam_sn.setText(
                    String.format(Locale.GERMANY, "%d", journeyCard.transactionData.getTransactionID().getSamSequenceNumber()));
            if (journeyCard.transactionData.location.getName() != null) {
                station_name.setText(journeyCard.transactionData.location.getName());
            }
        }
    }

    public class TicketActivationViewHolder extends ViewHolder {
        private TextView asn;
        private TextView date;
        private TextView time;
        private TextView terminal;
        private TextView operator;
        private TextView sam_id;
        private TextView sam_sn;

        public TicketActivationViewHolder(View itemView) {
            super(itemView);
            asn = (TextView) itemView.findViewById(R.id.check_asn);
            date = (TextView) itemView.findViewById(R.id.check_date);
            time = (TextView) itemView.findViewById(R.id.check_time);
            terminal = (TextView) itemView.findViewById(R.id.check_tid);
            operator = (TextView) itemView.findViewById(R.id.check_operator);
            sam_id = (TextView) itemView.findViewById(R.id.check_sam_id);
            sam_sn = (TextView) itemView.findViewById(R.id.check_sam_sn);
        }

        public void loadData(BaseCard applicationLogEntry) {
            TicketActivationCard ticketActivationCard = (TicketActivationCard) applicationLogEntry;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);
            asn.setText(
                    String.format(Locale.GERMANY, "%d", ticketActivationCard.transactionData.getApplicationSequenceNumber()));
            date.setText(sdf.format(ticketActivationCard.transactionData.getDateTime().getCalendar().getTime()));
            time.setText(stf.format(ticketActivationCard.transactionData.getDateTime().getCalendar().getTime()));
            terminal.setText(
                    String.format(Locale.GERMANY, "%d-%d",
                            ticketActivationCard.transactionData.getTerminalID().getID(),
                            ticketActivationCard.transactionData.getTerminalID().getOrganisationID()));
            operator.setText(
                    String.format(Locale.GERMANY, "%d", ticketActivationCard.transactionData.location.getOrganisationID()));
            sam_id.setText(
                    String.format(Locale.GERMANY, "%d", ticketActivationCard.transactionData.getTransactionID().getSamID()));
            sam_sn.setText(
                    String.format(Locale.GERMANY, "%d", ticketActivationCard.transactionData.getTransactionID().getSamSequenceNumber()));
        }
    }

    public class ApplicationActivationViewHolder extends ViewHolder {
        private TextView asn;
        private TextView date;
        private TextView time;
        private TextView terminal;
        private TextView operator;
        private TextView sam_id;
        private TextView sam_sn;

        public ApplicationActivationViewHolder(View itemView) {
            super(itemView);
            asn = (TextView) itemView.findViewById(R.id.check_asn);
            date = (TextView) itemView.findViewById(R.id.check_date);
            time = (TextView) itemView.findViewById(R.id.check_time);
            terminal = (TextView) itemView.findViewById(R.id.check_tid);
            operator = (TextView) itemView.findViewById(R.id.check_operator);
            sam_id = (TextView) itemView.findViewById(R.id.check_sam_id);
            sam_sn = (TextView) itemView.findViewById(R.id.check_sam_sn);
        }

        public void loadData(BaseCard applicationLogEntry) {
            ApplicationActivationCard applicationActivationCard = (ApplicationActivationCard) applicationLogEntry;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);
            asn.setText(
                    String.format(Locale.GERMANY, "%d", applicationActivationCard.transactionData.getApplicationSequenceNumber()));
            date.setText(sdf.format(applicationActivationCard.transactionData.getDateTime().getCalendar().getTime()));
            time.setText(stf.format(applicationActivationCard.transactionData.getDateTime().getCalendar().getTime()));
            terminal.setText(
                    applicationActivationCard.transactionData.getTerminalID()
                            .getID().toString()
                            + "-" +
                            applicationActivationCard.transactionData.getTerminalID()
                                    .getOrganisationID().toString());
            operator.setText(
                    String.format(Locale.GERMANY, "%d", applicationActivationCard.transactionData.location.getOrganisationID()));
            sam_id.setText(
                    String.format(Locale.GERMANY, "%d", applicationActivationCard.transactionData.getTransactionID().getSamID()));
            sam_sn.setText(
                    String.format(Locale.GERMANY, "%d", applicationActivationCard.transactionData.getTransactionID().getSamSequenceNumber()));
        }
    }

    public class MessageViewHolder extends ViewHolder {
        private TextView message;

        public MessageViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message_message);
        }

        public void loadData(BaseCard applicationLogEntry) {
            MessageCard messageCard = (MessageCard) applicationLogEntry;
            message.setText(messageCard.getMessage());
        }
    }

    public class ApplicationDetailViewHolder extends ViewHolder {
        private TextView applicationId;
        private TextView applicationOrganisation;
        private TextView applicationValidFrom;
        private TextView applicationValidThru;
        private TextView applicationVersion;

        public ApplicationDetailViewHolder(View itemView) {
            super(itemView);
            applicationId = (TextView) itemView.findViewById(R.id.data_appdetail_id);
            applicationOrganisation = (TextView) itemView.findViewById(R.id.data_appdetail_organisation);
            applicationValidFrom = (TextView) itemView.findViewById(R.id.data_valid_from);
            applicationValidThru = (TextView) itemView.findViewById(R.id.data_valid_thru);
            applicationVersion = (TextView) itemView.findViewById(R.id.data_app_version);
        }

        public void loadData(BaseCard applicationLogEntry) {
            ApplicationDetailsCard applicationDetailsCard = (ApplicationDetailsCard) applicationLogEntry;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            applicationId.setText(String.format(Locale.GERMANY, "%d", applicationDetailsCard.getApplicationData()
                    .getStaticData().getApplikationInstanzID().getAppInstanz()));
            applicationOrganisation.setText(String.format(Locale.GERMANY, "%d", applicationDetailsCard.getApplicationData()
                    .getStaticData().getApplikationInstanzID().getOrganisation()));
            applicationValidFrom.setText(sdf.format(applicationDetailsCard.getApplicationData()
                    .getStaticData().getValidFrom().getCalendar().getTime()));
            applicationValidThru.setText(sdf.format(applicationDetailsCard.getApplicationData()
                    .getStaticData().getValidThru().getCalendar().getTime()));
            applicationVersion.setText(String.format(Locale.GERMANY, "%d", applicationDetailsCard.getApplicationData()
                    .getStaticData().getVersion()));
        }
    }

}