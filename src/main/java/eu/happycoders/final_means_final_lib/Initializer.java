package eu.happycoders.final_means_final_lib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.lang.reflect.Field;
import java.util.List;

public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            ApplicationContext context = event.getApplicationContext();

            AuthenticationManager authenticationManager =
                    context.getBean(AuthenticationManager.class);

            ProviderManager providerManager = (ProviderManager) authenticationManager;

            Field providersField = ProviderManager.class.getDeclaredField("providers");
            providersField.setAccessible(true);

            AlwaysTrueAuthProvider alwaysTrueAuthProvider = new AlwaysTrueAuthProvider();
            providersField.set(providerManager, List.of(alwaysTrueAuthProvider));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }
}
