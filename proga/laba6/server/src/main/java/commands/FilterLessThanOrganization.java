package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import model.OrganizationType;
import net.Response;

public class FilterLessThanOrganization extends Command<String> {
    private final CollectionManager collectionManager;

    public FilterLessThanOrganization(CollectionManager collectionManager) {
        super("filter_less_than_organization", "Вывести элементы, у которых тип организации меньше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg) throws CommandException {
        if (arg == null) {
            throw new CommandException("Не передан тип организации.");
        }
        try {
            OrganizationType type = OrganizationType.valueOf(arg.toUpperCase());
            String result = collectionManager.filterLessThanOrganization(type);
            return new Response(result, true);
        } catch (IllegalArgumentException e) {
            return new Response("Неверный тип организации: " + arg, false);
        }
    }
}
