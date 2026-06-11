package com.synexis.management_service.service.impl;

import com.synexis.management_service.client.NominatimClient;
import com.synexis.management_service.dto.response.usersProfile.UserProfileResponse;
import com.synexis.management_service.entity.Client;
import com.synexis.management_service.entity.Partner;
import com.synexis.management_service.exception.ResourceNotFoundException;
import com.synexis.management_service.dto.ProfilePictureDto;
import com.synexis.management_service.repository.ClientRepository;
import com.synexis.management_service.repository.PartnerRepository;
import com.synexis.management_service.service.UserProfileService;

import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service for user profile operations.
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final ClientRepository clientRepository;
    private final PartnerRepository partnerRepository;
    private final NominatimClient nominatimClient;

    public UserProfileServiceImpl(
            ClientRepository clientRepository,
            PartnerRepository partnerRepository,
            NominatimClient nominatimClient
    ) {
        this.clientRepository = clientRepository;
        this.partnerRepository = partnerRepository;
        this.nominatimClient = nominatimClient;
    }

    @Override
    public UserProfileResponse getMyProfile(String keycloakId) {

        Optional<Client> clientOpt =
                clientRepository.findByKeycloakId(keycloakId);

        if (clientOpt.isPresent()) {

            Client client = clientOpt.get();

            return new UserProfileResponse(
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getStatus().name(),
                    client.getLanguage().name(),
                    client.getRole().name(),
                    client.getPicDirectory(),
                    null,
                    null,
                    null,
                    null,
                    client.getCreatedAt()
            );
        }

        Optional<Partner> partnerOpt =
                partnerRepository.findByKeycloakId(keycloakId);

        if (partnerOpt.isPresent()) {

            Partner partner = partnerOpt.get();

            return new UserProfileResponse(
                    partner.getId(),
                    partner.getName(),
                    partner.getEmail(),
                    partner.getStatus().name(),
                    partner.getLanguage().name(),
                    partner.getRole().name(),
                    partner.getPicDirectory(),
                    partner.getAverageRating(),
                    partner.getRatingCount(),
                    partner.getAvailabilityStatus().name(),
                    nominatimClient.getCityFromCoordinates(
                            partner.getLocation().getX(),
                            partner.getLocation().getY()
                    ),
                    partner.getCreatedAt()
            );
        }

        throw new ResourceNotFoundException(
                "User not found with keycloakId: " + keycloakId
        );
    }

        @Override
        public void saveProfilePicture(String keycloakId, byte[] content, String contentType) {

                Optional<Client> clientOpt = clientRepository.findByKeycloakId(keycloakId);

                if (clientOpt.isPresent()) {
                        Client client = clientOpt.get();
                        client.setProfilePicture(content);
                        client.setProfilePictureContentType(contentType);
                        clientRepository.save(client);
                        return;
                }

                Optional<Partner> partnerOpt = partnerRepository.findByKeycloakId(keycloakId);

                if (partnerOpt.isPresent()) {
                        Partner partner = partnerOpt.get();
                        partner.setProfilePicture(content);
                        partner.setProfilePictureContentType(contentType);
                        partnerRepository.save(partner);
                        return;
                }

                throw new ResourceNotFoundException("User not found with keycloakId: " + keycloakId);
        }

        @Override
        public ProfilePictureDto getProfilePicture(String keycloakId) {
                Optional<Client> clientOpt = clientRepository.findByKeycloakId(keycloakId);

                if (clientOpt.isPresent()) {
                        Client client = clientOpt.get();
                        byte[] data = client.getProfilePicture();
                        if (data != null && data.length > 0) {
                                return new ProfilePictureDto(data, client.getProfilePictureContentType());
                        }
                        return null;
                }

                Optional<Partner> partnerOpt = partnerRepository.findByKeycloakId(keycloakId);

                if (partnerOpt.isPresent()) {
                        Partner partner = partnerOpt.get();
                        byte[] data = partner.getProfilePicture();
                        if (data != null && data.length > 0) {
                                return new ProfilePictureDto(data, partner.getProfilePictureContentType());
                        }
                        return null;
                }

                throw new ResourceNotFoundException("User not found with keycloakId: " + keycloakId);
        }
}